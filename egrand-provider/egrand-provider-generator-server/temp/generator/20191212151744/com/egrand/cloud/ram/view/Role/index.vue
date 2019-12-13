<template>
    <div>
        <Card shadow>
            <div class="search-con search-con-top">
                <ButtonGroup>
                    <Button :disabled="hasAuthority('RoleEdit')?false:true"  class="search-btn" type="primary" @click="handleModal()">
                        <span>添加</span>
                    </Button>
                </ButtonGroup>
            </div>
            <Table :columns="columns" :data="data" :loading="loading">
                <template slot="action" slot-scope="{ row }">
                    <a :disabled="hasAuthority('RoleEdit')?false:true"   @click="handleModal(row)">
                        编辑</a>&nbsp;
                    <Dropdown v-show="hasAuthority('RoleEdit')" transfer ref="dropdown" @on-click="handleClick($event,row)">
                        <a href="javascript:void(0)">
                            <span>更多</span>
                            <Icon type="ios-arrow-down"></Icon>
                        </a>
                        <DropdownMenu slot="list">
                            <DropdownItem  name="remove">删除</DropdownItem>
                        </DropdownMenu>
                    </Dropdown>&nbsp;
                </template>
            </Table>
            <Page :total="pageInfo.total" :current="pageInfo.page" :page-size="pageInfo.limit" show-elevator show-sizer
                  show-total
                  @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
        </Card>
        <Modal v-model="modalVisible"
               :title="modalTitle"
               width="40"
               @on-cancel="handleReset">
            <div>
                <Form ref="form" :model="formItem" :rules="formItemRules" :label-width="100">
                    <FormItem label="" prop="comments">
                        <Input v-model="formItem.comments"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="createDate">
                        <Input v-model="formItem.createDate"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="roleCode">
                        <Input v-model="formItem.roleCode"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="roleName">
                        <Input v-model="formItem.roleName"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="status">
                        <Input v-model="formItem.status"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="updateDate">
                        <Input v-model="formItem.updateDate"  placeholder="请输入内容"></Input>
                    </FormItem>
                </Form>
                <div class="drawer-footer">
                    <Button type="default" @click="handleReset">取消</Button>&nbsp;
                    <Button type="primary" @click="handleSubmit" :loading="saving">保存</Button>
                </div>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Role from '@/api/Role'

    export default {
        name: 'Role',
        data () {
            return {
                loading: false,
                saving: false,
                modalVisible: false,
                modalTitle: '',
                pageInfo: {
                    total: 0,
                    page: 1,
                    limit: 10
                },
                formItemRules: {
                    comments: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    createDate: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    roleCode: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    roleName: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    status: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    updateDate: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                },
                formItem: {
                    id: '',
                    comments: '',
                    createDate: '',
                    roleCode: '',
                    roleName: '',
                    status: '',
                    updateDate: '',
                },
                columns: [
                    {
                        title: '',
                        key: 'comments',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'createDate',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'roleCode',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'roleName',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'status',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'updateDate',
                        width: 100
                    },
                    {
                        title: '操作',
                        slot: 'action',
                        fixed: 'right',
                        width: 120
                    }
                ],
                data: []
            }
        },
        methods: {
            handleModal (data) {
                if (data) {
                    this.modalTitle = '编辑'
                    this.formItem = Object.assign({}, this.formItem, data)
                } else {
                    this.modalTitle = '添加'
                }
                this.modalVisible = true
            },
            handleReset () {
                const newData = {
                    id: '',
                    comments: '',
                    createDate: '',
                    roleCode: '',
                    roleName: '',
                    status: '',
                    updateDate: '',
                }
                this.formItem = newData
                //重置验证
                let form  = this.$refs['form']
                form.resetFields()
                        this.modalVisible = false
                this.saving = false
            },
            handleSubmit () {
                let form  = this.$refs['form']
                form.validate((valid) => {
                    if (valid) {
                        this.saving = true
                        if (this.formItem.id) {
                        Role.update(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.$Message.success('保存成功')
                                this.handleReset()
                             }
                            this.handleSearch()
                        }).finally(() => {this.saving = false})
                    } else {
                       Role.add(this.formItem).then(res => {
                            this.handleReset()
                            this.handleSearch()
                            if (res.code === 0) {
                                this.$Message.success('保存成功')
                            }
                          }).finally(() => {this.saving = false})
                        }
                   }
                })
            },
            handleSearch (page) {
                if (page) {
                    this.pageInfo.page = page
                }
                this.loading = true
                Role.list({page: this.pageInfo.page, limit: this.pageInfo.limit}).then(res => {
                    this.data = res.data.records
                    this.pageInfo.total = parseInt(res.data.total)
                }).finally(() => {this.loading = false})
            },
            handlePage (current) {
                this.pageInfo.page = current
                this.handleSearch()
            },
            handlePageSize (size) {
                this.pageInfo.limit = size
                this.handleSearch()
            },
            handleRemove (data) {
               let modal = this.$Modal
               modal.confirm({
                    title: '确定删除吗？',
                    onOk: () => {
                        Role.remove(data.id).then(res => {
                            if (res.code === 0) {
                                this.pageInfo.page = 1;
                                this.$Message.success('删除成功');
                            }
                            this.handleSearch();
                        })
                    }
                })
            },
            handleClick (name, row) {
                switch (name) {
                    case 'remove':
                        this.handleRemove(row)
                        break
                }
            }
        },
        mounted: function () {
            this.handleSearch()
        }
    }
</script>
