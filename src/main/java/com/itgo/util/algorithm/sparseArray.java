package com.itgo.util.algorithm;

/**
 * @author byl
 * @date 2019-05-30
 */
public class sparseArray {

    public sparseArray(){

    }

    private  int[] [] obj ;
    private  int moto ;

    /**
     * @param obj  表示入参数组
     **/
    public sparseArray(int [] [] obj   ){
        this.obj = obj;
    }

    /**
     * 把二维数组转换为稀疏数组
     * @return 稀疏数组
     */
    public int  [] [] toSparse (){
        int sum = 0; //个数
        for (int i = 0; i < obj.length; i++) {
            for (int j = 0; j < obj[i].length; j++) {
                if (obj[i][j] != moto){
                    sum++;
                }
            }
        }

        int [] [] sparseArr = new int[sum+1][3];
        sparseArr [0][0] = obj.length;
        sparseArr [0][1] = obj[0].length;
        sparseArr [0][2] = sum;

        int count = 0 ;
        for (int i = 0; i < obj.length ; i++) {
            for (int j = 0; j < obj[i].length ; j++) {

                if(obj[i][j] != moto){
                    count ++ ;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = obj[i][j];
                }
            }

        }
        return sparseArr;
    }

    /**
     * 把稀疏数组转换为二维数组
     * @param sparseArr 稀疏数组
     * @return 二维数组
     */
    public int [][] toArray(int [][] sparseArr){
        int [][] twoArray = new int [sparseArr.length][sparseArr[0].length];
        for (int i = 1; i < twoArray.length; i++) {
            for (int j = 0; j < twoArray[i].length ; j++) {
                twoArray [sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
            }
        }
        return twoArray;
    }


}
