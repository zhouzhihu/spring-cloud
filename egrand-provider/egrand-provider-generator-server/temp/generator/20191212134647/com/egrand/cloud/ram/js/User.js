import request from '@/libs/request'

/**
 * 获取分页数据
 */
const list = ({page, limit}) => {
    const params = {page: page, limit: limit}
    return request({
        url: '/ram/user/list',
        params,
        method: 'get'
    })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
    const params = {
        id: id
    }
    return request({
        url:'/ram/user/get',
        params,
        method: 'get'
    })
}

/**
 * 添加数据
 */
const add = (data) => {
    return request({
        url: '/ram/user/add',
        data,
        method: 'post'
    })
}

/**
 * 更新数据
 */
const update = (data) => {
    return request({
        url: '/ram/user/update',
        data,
        method: 'post'
    })
}

/**
 * 删除数据
 * @param apiId
 */
const remove = (id) => {
    const data = {
        id: id
    }
    return request({
        url: '/ram/user/remove',
        data,
        method: 'post'
    })
}

export default {
    list,
    get,
    add,
    update,
    remove
}
