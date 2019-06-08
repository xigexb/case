package com.itgo.util.array;

import com.itgo.exception.ObjectNotNullException;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/6/8 11:48
 * @description desc:
 */
public class ArrayUtil {


    /**
     * 转稀疏数组
     *
     * @param formArray
     * @return
     */
    public static Object[][] toSparseArray(Object[][] formArray) {
        try {
            if (formArray == null) {
                throw new ObjectNotNullException("data must not null");
            }
            Integer rows = new Integer(0);
            Integer cells = new Integer(0);
            Integer sumValueCount = new Integer(0);
            for (Object[] ts : formArray) {
                cells = 0;
                rows++;
                for (Object t : ts) {
                    cells++;
                    if (t != null) {
                        sumValueCount++;
                    }
                }
            }
            Object[][] sparseArray = new Object[sumValueCount + 1][3];
            sparseArray[0][0] = rows;
            sparseArray[0][1] = cells;
            sparseArray[0][2] = sumValueCount;

            int index = 0;
            for (Integer i = 0; i < rows; i++) {
                for (Integer j = 0; j < cells; j++) {
                    if (formArray[i][j] != null) {
                        index++;
                        sparseArray[index][0] = i;
                        sparseArray[index][1] = j;
                        sparseArray[index][2] = formArray[i][j];
                    }
                }
            }
            return sparseArray;
        } catch (ObjectNotNullException e) {

        }
        return null;
    }


    /**
     * 恢复二维数组
     * @param sparseArray
     * @return
     */
    public static Object[][] toTwoDimensionalArray(Object[][] sparseArray) {
        try {
            if (sparseArray == null) {
                throw new ObjectNotNullException("data must not null");
            }

            Object[][] twoDimensionalArray = new Object[Integer.valueOf(sparseArray[0][0].toString())][Integer.valueOf(sparseArray[0][1].toString())];
            for (int i = 1; i < sparseArray.length; i++) {
                twoDimensionalArray[Integer.valueOf(sparseArray[i][0].toString())][Integer.valueOf(sparseArray[i][1].toString())] = Integer.valueOf(sparseArray[i][2].toString());
            }
            return twoDimensionalArray;

        }catch (ObjectNotNullException e){

        }
        return null;
    }

}
