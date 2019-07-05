package com.itgo.baseface;


/**
 * 接口层数据校验
 */
public interface BaseInterface {

    /**
     * 新增基础数据集校验
     * @return
     */
    String insertCheckParams();

    /**
     * 修改基础数据集校验
     * @return
     */
    String updateCheckParams();

    /**
     * 修改基础数据集校验
     * @return
     */
    String selectCheckParams();

    /**
     * 修改基础数据集校验
     * @return
     */
    String deleteCheckParams();


}
