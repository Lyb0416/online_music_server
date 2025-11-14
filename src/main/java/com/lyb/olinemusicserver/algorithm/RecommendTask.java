package com.lyb.olinemusicserver.algorithm;

import com.lyb.olinemusicserver.model.domain.Collect;
import com.lyb.olinemusicserver.model.domain.DownloadRecord;
import com.lyb.olinemusicserver.model.domain.PlayRecord;
import com.lyb.olinemusicserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RecommendTask {

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private SongService songService;
    @Autowired
    private PlayRecordService playRecordService;
    @Autowired
    private DownloadRecordService downLoadRecordService;
    @Autowired
    private CollectService collectionService;
    @Autowired
    private RecommendService recommendService;

    /**
     * KNN k值
     * 目前系统用户很少
     */
    public static final int K = 10;

    /**
     * 基于最近邻用户的协同过滤给用户推荐歌曲的数量 n值
     * 歌曲很少
     */
    public static final int N = 15;

    /**
     * @Description: 推荐歌曲方法
     **/
    @Scheduled(cron = "*/30 * * * * ?") //使用 Cron 表达式配置每 30s 执行一次该方法。
    public void recommend(){
        /** Step1:准备偏好数据 */
        //用户-歌曲 推荐列表
        Map<Integer, Integer[]> user2songRecMatrix = new HashMap<Integer, Integer[]>();
        //获取用户
        List<Integer> userIdList = consumerService.getAllUserIds();
        //获取歌曲
        List<Integer> songIdList = songService.getAllSongIds();
        //获取用户的播放记录
        List<PlayRecord> playList = playRecordService.getAllRecords();
        //获取用户的下载记录
        List<DownloadRecord> downloadList = downLoadRecordService.getAllRecords();
        //获取用户的收藏记录
        List<Collect> collectionList = collectionService.getAllRecords();
        //用户-歌曲 “评分”矩阵
        Map<Integer, float[]> user2songRatingMatrix = DataTranslate.getFrequencyMatrix(userIdList, songIdList,
                downloadList, playList, collectionList);

        /** Step2:计算用户相似性，Step3：获取用户的 K 个近邻用户 */
        Map<Integer, Integer[]> userKNNMatrix = UserKNN.getKNN(userIdList, user2songRatingMatrix, K);

        /** Step4：生成推荐数据：基于用户相似性的协同过滤 */
        user2songRecMatrix = CollaborativeFiltering.userKNNBasedCF(userIdList, userKNNMatrix,
                user2songRatingMatrix, songIdList, N);
        //打印推荐数据
        //user2songRecMatrix.forEach((userId, songIds) -> System.out.println("为用户id = " + userId + "推荐的歌曲是：" + Arrays.toString(songIds)));
        recommendService.updatePersonalRecInfo(user2songRecMatrix);

    }
}
