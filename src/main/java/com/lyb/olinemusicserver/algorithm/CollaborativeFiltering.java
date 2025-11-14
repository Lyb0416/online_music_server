package com.lyb.olinemusicserver.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//基于userKNN的协同过滤算法
public class CollaborativeFiltering {

    /**
     * 基于最近邻用户产生协同过滤的推荐结果
     * @param userIdList            用户Id列表
     * @param userKNNMatrix         用户KNN矩阵
     * @param user2songRatingMatrix 用户歌曲“评分”矩阵
     * @param songIdList            歌曲Id列表
     * @param n                     推荐的前n首歌曲
     * @return 用户歌曲推荐结果矩阵.userId, [recSongId1, recSongId2...recSongIdn]
     */
    public static Map<Integer, Integer[]> userKNNBasedCF(List<Integer> userIdList,
                                                         final Map<Integer, Integer[]> userKNNMatrix, final Map<Integer, float[]> user2songRatingMatrix,
                                                         final List<Integer> songIdList, final int n) {
        final Map<Integer, Integer[]> user2songRecMatrix = new HashMap<Integer, Integer[]>();
        userIdList.forEach(new Consumer<Integer>() {

            public void accept(Integer curUserId) {
                Integer[] knnIdArray = userKNNMatrix.get(curUserId);// knnIdArray=当前用户的最近邻用户数组
                /**
                 * 对于每一首当前用户没有听过的歌曲
                 * 协同得分为：
                 * 其k个最近邻用户对该歌曲的“评分”的聚合
                 **/
                float[] curUserRatings = user2songRatingMatrix.get(curUserId);// curUserRatings=当前用户的歌曲打分数组
                //为用户建立一个最小堆来存放最高的前n首歌曲
                MininumHeap mininumHeap = new MininumHeap(n);
                for (int i = 0; i < curUserRatings.length; i++) {
                    //对于没有听过的歌曲=>其评分看最近邻用户的评分=>其协同得分等于最近邻用户对该歌曲的“评分”的聚合
                    /**
                     * 这里需要注意的是，浮点数不能用==来比较
                     * 故这里用 curUserRatings[i]<0.01f 来表示 curUserRatings[i]==0f
                     */
                    if (curUserRatings[i] < 0.01f) {
                        for (int knnIndex = 0; knnIndex < knnIdArray.length; knnIndex++) { // 遍历当前用户的最近邻用户数组
                            int knnId = knnIdArray[knnIndex];
                            // 获取当前近邻用户的歌曲打分数组
                            float[] knnUserRatings = user2songRatingMatrix.get(knnId);
                            // 对当前音乐的打分等于近邻用户对这首歌曲打分的和
                            curUserRatings[i] += knnUserRatings[i];
                        }
                        //这里的聚合策略取均值
                        curUserRatings[i] /= knnIdArray.length;
                        // 获取当前遍历到的歌曲id
                        int curSongId = songIdList.get(i);
                        // 将当前打分完毕的歌曲id和当前用户对该歌曲的评分封装成一个树节点，放入容量为k的堆中
                        mininumHeap.addElement(new TreeNode(curSongId, curUserRatings[i]));
                    }
                }
                /**
                 * 对该用户没有听过的歌曲,协同得分完成，选取n个得分最高的项目作为推荐
                 */
                int trueNumber = n;
                //如果推荐的歌曲少于计划推荐的n首(处理歌曲很少的情况)
                if (mininumHeap.getCurHeapSize() < n) {
                    trueNumber = mininumHeap.getCurHeapSize();
                }
                Integer[] curUserRecSongId = new Integer[trueNumber];
                for (int i = 0; i < trueNumber; i++) {
                    int recSongId = mininumHeap.getArray()[i].id;
                    curUserRecSongId[i] = recSongId;
                }
                user2songRecMatrix.put(curUserId, curUserRecSongId);
            }
        });
        return user2songRecMatrix;
    }
}

