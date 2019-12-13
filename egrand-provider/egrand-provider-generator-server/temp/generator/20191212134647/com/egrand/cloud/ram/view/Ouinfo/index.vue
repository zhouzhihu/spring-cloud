<template>
    <div>
        <Card shadow>
            <div class="search-con search-con-top">
                <ButtonGroup>
                    <Button :disabled="hasAuthority('OuinfoEdit')?false:true"  class="search-btn" type="primary" @click="handleModal()">
                        <span>添加</span>
                    </Button>
                </ButtonGroup>
            </div>
            <Table :columns="columns" :data="data" :loading="loading">
                <template slot="action" slot-scope="{ row }">
                    <a :disabled="hasAuthority('OuinfoEdit')?false:true"   @click="handleModal(row)">
                        编辑</a>&nbsp;
                    <Dropdown v-show="hasAuthority('OuinfoEdit')" transfer ref="dropdown" @on-click="handleClick($event,row)">
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
                    <FormItem label="" prop="address">
                        <Input v-model="formItem.address"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="description">
                        <Input v-model="formItem.description"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="email">
                        <Input v-model="formItem.email"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="field1">
                        <Input v-model="formItem.field1"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="field2">
                        <Input v-model="formItem.field2"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="field3">
                        <Input v-model="formItem.field3"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="field4">
                        <Input v-model="formItem.field4"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="ouCode">
                        <Input v-model="formItem.ouCode"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="ouFullCode">
                        <Input v-model="formItem.ouFullCode"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="ouFullName">
                        <Input v-model="formItem.ouFullName"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="ouName">
                        <Input v-model="formItem.ouName"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="ouType">
                        <Input v-model="formItem.ouType"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="telephone">
                        <Input v-model="formItem.telephone"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="zipCode">
                        <Input v-model="formItem.zipCode"  placeholder="请输入内容"></Input>
                    </FormItem>
                    <FormItem label="" prop="parentId">
                        <Input v-model="formItem.parentId"  placeholder="请输入内容"></Input>
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
    import Ouinfo from '@/api/Ouinfo'

    export default {
        name: 'Ouinfo',
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
                    address: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    description: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    field1: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    field2: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    field3: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    field4: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    ouCode: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    ouFullCode: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    ouFullName: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    ouName: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    ouType: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    telephone: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    zipCode: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    parentId: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                },
                formItem: {
                    id: '',
                    address: '',
                    description: '',
                    email: '',
                    field1: '',
                    field2: '',
                    field3: '',
                    field4: '',
                    ouCode: '',
                    ouFullCode: '',
                    ouFullName: '',
                    ouName: '',
                    ouType: '',
                    telephone: '',
                    zipCode: '',
                    parentId: '',
                },
                columns: [
                    {
                        title: '',
                        key: 'address',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'description',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'email',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'field1',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'field2',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'field3',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'field4',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'ouCode',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'ouFullCode',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'ouFullName',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'ouName',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'ouType',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'telephone',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'zipCode',
                        width: 100
                    },
                    {
                        title: '',
                        key: 'parentId',
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
                    address: '',
                    description: '',
                    email: '',
                    field1: '',
                    field2: '',
                    field3: '',
                    field4: '',
                    ouCode: '',
                    ouFullCode: '',
                    ouFullName: '',
                    ouName: '',
                    ouType: '',
                    telephone: '',
                    zipCode: '',
                    parentId: '',
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
                        Ouinfo.update(this.formItem).then(res => {
                            if (res.code === 0) {
                                this.$Message.success('保存成功')
                                this.handleReset()
                             }
                            this.handleSearch()
                        }).finally(() => {this.saving = false})
                    } else {
                       Ouinfo.add(this.formItem).then(res => {
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
                Ouinfo.list({page: this.pageInfo.page, limit: this.pageInfo.limit}).then(res => {
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
                        Ouinfo.remove(data.id).then(res => {
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
