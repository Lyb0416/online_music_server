package com.lyb.olinemusicserver.algorithm;

import com.lyb.olinemusicserver.model.domain.Collect;
import com.lyb.olinemusicserver.model.domain.DownloadRecord;
import com.lyb.olinemusicserver.model.domain.PlayRecord;
import com.lyb.olinemusicserver.util.TypeConvertUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class DataTranslate {
    private final static float PLAY_SCORE = 1f;
    private final static float DOWNLOAD_SCORE = 2f;
    private final static float COLLECTION_SCORE = 5f;
    private final static float MAX_SCORE = 10f;
    private final static int SONG_ID_SET_KEY = 0;

    /**
     * 构建用户频率矩阵来近似用户评分，对于某些系统而言，我们是不可能获取到用户对某些项目的评分的，但是我们可以利用用户的
     * 行为习惯来反映用户的“评分”，比如一个用户常常收听某一首歌，那么我们可以推断该用户喜欢该歌曲的可能性很大.
     * 总分10分，主动播放一次1分，下载2分，收藏5分，如果超过10分，按10分计算.
     *
     * @param userIdList     用户Id列表
     * @param songIdList     歌曲Id列表
     * @param downloadList   用户的下载记录列表
     * @param playList       用户的播放记录列表
     * @param collectionList 用户的收藏记录列表
     * @return 用户Id-歌曲Id 频率矩阵
     */
    public static Map<Integer, float[]> getFrequencyMatrix(List<Integer> userIdList, final List<Integer> songIdList,
                                                           List<DownloadRecord> downloadList, List<PlayRecord> playList, List<Collect> collectionList) {
        /**
         * 构建用户-歌曲评分矩阵（基于用户多种行为的综合得分）
         *
         *  用户列表：[10, 20]
         *  歌曲列表：[501, 502, 503, 504]（songLen = 4）
         *
         *  最终结构示例：
         *      {
         *          "10": [5.0, 10.0, 2.0, 0.0],   // 用户10：歌501收藏=5分，502满分10分，503播放2次，504无行为
         *          "20": [0.0, 1.0, 7.0, 10.0]    // 用户20：歌503综合=7分，504满分10分
         *      }
         *
         *             501   502   503   504
         *      u10     5     10     2     0
         *      u20     0      1     7    10
         *
         *  意义：
         *      userId → float[songLen]
         *      每个 float[] 就是该用户对所有歌曲的评分向量
         */
        final Map<Integer, float[]> user2songRatingMatrix = new HashMap<Integer, float[]>();
        final int songLen = songIdList.size();
        /**
         * 构建用户-歌曲 下载映射（是否下载过，不计次数）
         *
         *    结构：Map<userId, Map<0, Set<songId>>>
         *
         *    示例：
         *        downloadList:
         *            user1 下载了 [101, 103]
         *            user2 下载了 [102]
         *
         *    得到：
         *        {
         *           1 : { 0 : [101, 103] },
         *           2 : { 0 : [102] }
         *        }
         *
         *    意义：
         *         key=0 代表“该用户下载过的所有歌曲的集合”
         *         只记录是否出现，不记录次数
         */
        final Map<Integer, Map<Integer, Set<Integer>>> userId2songIdDownloadMap = getUserId2songIdRecordMap(downloadList, false);
        /**
         * 构建用户-歌曲 收藏映射（是否收藏，不计次数）
         *
         *    结构：Map<userId, Map<0, Set<songId>>>
         *
         *    示例：
         *        collectionList:
         *            user1 收藏了 [101]
         *            user3 收藏了 [102, 103]
         *
         *    得到：
         *        {
         *           1 : { 0 : [101] },
         *           3 : { 0 : [102, 103] }
         *        }
         *
         *    意义：
         *         key=0 → 该用户收藏过的所有歌曲
         *         用来给评分矩阵加 5 分
         */
        final Map<Integer, Map<Integer, Set<Integer>>> userId2songIdCollectionMap = getUserId2songIdRecordMap(collectionList, false);
        /**
         * 构建用户-歌曲 播放次数映射（需要计数）
         *
         *    结构：Map<userId, Map<songId 或 0, Set<整数>>>
         *
         *    内层 Map 有两类 key：
         *        key = 0     → 这个用户出现过的所有歌曲集合
         *        key = songId → 该歌曲的播放次数（Set 中只有一个元素）
         *
         *    示例：
         *        playList:
         *            user1 播放 101 两次，播放 102 一次
         *
         *    得到：
         *        {
         *           1 : {
         *                 0 : [101, 102],   // 出现过的歌曲
         *               101 : [2],          // 101 播放次数
         *               102 : [1]           // 102 播放次数
         *              }
         *        }
         *
         *    意义：
         *         播放次数 用来加 PLAY_SCORE * count
         */
        final Map<Integer, Map<Integer, Set<Integer>>> userId2songIdPlayMap = getUserId2songIdRecordMap(playList, true);

        userIdList.forEach(new Consumer<Integer>() {

            public void accept(Integer userId) {
                float[] curUserRatingArray = new float[songLen];
                int songIndex = 0;
                //处理每一首歌曲
                for (Integer songId : songIdList) {
                    /**
                     * 处理下载，这里不考虑下载次数
                     *
                     * userId2songIdDownloadMap.get(userId) != null 确保用户下载过歌曲
                     * userId2songIdDownloadMap.get(userId).get(SONG_ID_SET_KEY) != null 确保用户下载过该歌曲
                     */
                    if (userId2songIdDownloadMap.get(userId) != null && userId2songIdDownloadMap.get(userId).get(SONG_ID_SET_KEY).contains(songId)) {
                        //当前用户下载过的歌曲
                        curUserRatingArray[songIndex] += DOWNLOAD_SCORE;
                    }

                    /**
                     * 处理收藏，这里没有次数
                     *
                     * userId2songIdCollectionMap.get(userId) != null 确保用户收藏过歌曲
                     * userId2songIdCollectionMap.get(userId).get(SONG_ID_SET_KEY) != null 确保用户收藏过该歌曲
                     */
                    if (userId2songIdCollectionMap.get(userId) != null && userId2songIdCollectionMap.get(userId).get(SONG_ID_SET_KEY).contains(songId)) {
                        //当前用户收藏的歌曲
                        curUserRatingArray[songIndex] += COLLECTION_SCORE;
                    }

                    /**
                     * 处理播放，考虑播放次数
                     *
                     * userId2songIdPlayMap.get(userId) != null 确保用户播放过歌曲
                     * userId2songIdPlayMap.get(userId).get(SONG_ID_SET_KEY) != null 确保用户播放过该歌曲
                     */
                    if (userId2songIdPlayMap.get(userId) != null && userId2songIdPlayMap.get(userId).get(SONG_ID_SET_KEY).contains(songId)) {
                        //当前用户播放过的歌曲
                        int count = userId2songIdPlayMap.get(userId).get(songId).iterator().next();
                        curUserRatingArray[songIndex] += PLAY_SCORE * count;
                    }

                    /**
                     * 处理最大得分，超过最大得分，记为最大得分
                     */
                    if (curUserRatingArray[songIndex] > MAX_SCORE) {
                        curUserRatingArray[songIndex] = MAX_SCORE;
                    }
                    //处理下一首歌
                    songIndex++;
                }
                //处理完一个用户
                user2songRatingMatrix.put(userId, curUserRatingArray);
            }

        });
        return user2songRatingMatrix;
    }

    /**
     * 获取用户Id - 歌曲Id 的映射Map
     * @param recordList 包含userId，songId的记录列表
     * @param isCount    是否需要计数。如果true，则Integer[1]存放计数。
     * @return 两层Map
     * 第一层Map<Integer,Map> 每个userId拥有一个自己的Map：
     *  userId,userSetMap
     * 第二层Map<Integer,Set> 用户自己的Map里面存放两个东西：
     * （1）为每首歌曲计数songId,CountSet；
     * （2）存放出现过的歌曲songSetFlag,SongIdSet：
     */
    private static <T> Map<Integer, Map<Integer, Set<Integer>>> getUserId2songIdRecordMap(final List<T> recordList, final boolean isCount) {
        final Map<Integer, Map<Integer, Set<Integer>>> userId2songIdRecordMap = new HashMap<Integer, Map<Integer, Set<Integer>>>();

        recordList.forEach(new Consumer<T>() {

            /**
             * forEach（）方法是Iterable<T>接口中的一个方法。
             * Java容器中，所有的Collection子类（List、Set）会实现Iteratable接口以实现foreach功能。
             * forEach（）方法里面有个Consumer类型，它是Java8新增的一个消费型函数式接口，
             * 其中的accept(T t)方法代表了接受一个输入参数并且无返回的操作。
             * @param t
             */
            public void accept(T t) {
                try {
                    //利用反射获和泛型获取不同类型表的相同属性
                    //经常需要对特定对象转换成我想要的JSON对象，为了实现通用性想到用反射去实现这个过程。
                    //获取当前对象的成员变量的类型
                    //对成员变量重新设值
                    Field userIdField = t.getClass().getDeclaredField("userId");
                    Field songIdField = t.getClass().getDeclaredField("songId");
                    userIdField.setAccessible(true);
                    songIdField.setAccessible(true);
                    int userId = TypeConvertUtil.toInt(userIdField.get(t));
                    int songId = TypeConvertUtil.toInt(songIdField.get(t));
                    //不需要计数
                    if (!isCount) {
                        //map外层的userId已经存在
                        if (userId2songIdRecordMap.containsKey(userId)) {
                            //获取当前用户的记录集合Map
                            Map<Integer, Set<Integer>> curRecordSetMap = userId2songIdRecordMap.get(userId);
                            //将当前歌曲添加到当前用户的记录集合中
                            curRecordSetMap.get(SONG_ID_SET_KEY).add(songId);
                        } else {
                            Map<Integer, Set<Integer>> curRecordSetMap = new HashMap<Integer, Set<Integer>>();
                            //创建记录歌曲Id的集合
                            Set<Integer> curSongIdSet = new HashSet<Integer>();
                            curSongIdSet.add(songId);
                            curRecordSetMap.put(SONG_ID_SET_KEY, curSongIdSet);
                            userId2songIdRecordMap.put(userId, curRecordSetMap);
                        }
                    } else {  // 需要计数
                        //map外层的userId已经存在
                        if (userId2songIdRecordMap.containsKey(userId)) {
                            //获取当前用户的记录集合Map
                            Map<Integer, Set<Integer>> curRecordSetMap = userId2songIdRecordMap.get(userId);
                            //将当前歌曲添加到当前用户的记录集合中
                            curRecordSetMap.get(SONG_ID_SET_KEY).add(songId);
                            //计数
                            count(songId, curRecordSetMap);
                        } else {
                            Map<Integer, Set<Integer>> curRecordSetMap = new HashMap<Integer, Set<Integer>>();
                            //创建记录歌曲Id的集合
                            Set<Integer> curSongIdSet = new HashSet<Integer>();
                            curSongIdSet.add(songId);
                            curRecordSetMap.put(SONG_ID_SET_KEY, curSongIdSet);
                            userId2songIdRecordMap.put(userId, curRecordSetMap);
                            //计数
                            count(songId, curRecordSetMap);
                        }
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            private void count(int songId, Map<Integer, Set<Integer>> curRecordSetMap) {
                /**
                 * 计数,如果Map<songId,count>已经存在，则直接计数+1
                 */
                if (curRecordSetMap.containsKey(songId)) {
                    //获取当前用户歌曲的计数集合(只有一个元素)
                    Set<Integer> curCountSet = curRecordSetMap.get(songId);
                    int cnt = curCountSet.iterator().next() + 1;
                    curCountSet.clear();
                    curCountSet.add(cnt);
                } else {
                    Set<Integer> curCountSet = new HashSet<Integer>();
                    curCountSet.add(1);
                    curRecordSetMap.put(songId, curCountSet);
                }
            }
        });
        return userId2songIdRecordMap;
    }
}
