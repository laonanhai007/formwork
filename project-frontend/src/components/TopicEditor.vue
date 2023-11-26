<template>
    <div>
        <el-drawer :model-value="show"
                   direction="btt"
                   :size="600"
                   :close-on-click-modal="false"
        @close="emit('close')">
            <template #header>
                <div style="font-weight: bold">发表新的帖子</div>
            </template>
            <div style="display: flex;gap: 10px">
                <div style="width: 140px;">
                    <el-select placeholder="选择帖子类型..." v-model="editor.type">
                        <el-option v-for="item in types" :value="item.id" :label="item.name"/>
                    </el-select>
                </div>
                <div style="flex: 1">
                    <el-input placeholder="帖子标题..." :prefix-icon="Document"/>
                </div>
            </div>
            <div style="margin-top: 15px ">
                <quill-editor v-model:content="editor.text" style="height: 350px;border-radius: 0 0 5px 5px"
                              placeholder="开始编辑"/>
            </div>
            <el-row style="margin-top: 10px">
                <el-col :span="12">
                    <div style="font-size: 13px;color: gray">当前字数 666 (最大支持10000字)</div>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <el-button type="success" plain :icon="Position">立即发布</el-button>
                </el-col>
            </el-row>
        </el-drawer>
    </div>
</template>

<script setup>
    import {QuillEditor} from '@vueup/vue-quill'
    import '@vueup/vue-quill/dist/vue-quill.snow.css';
    import {Document, Position} from "@element-plus/icons-vue";
    import {reactive} from "vue";

    defineProps({
        show: Boolean
    })
    const types = [
        {id: 1, name: '日常闲聊', desc: '在这里分享你的各种日常'},
        {id: 2, name: '真诚交友', desc: '在校园里寻找与自己志同道合的朋友'},
        {id: 3, name: '问题反馈', desc: '反馈你在校园里遇到的问题'},
        {id: 4, name: '恋爱官宣', desc: '向大家展示你的恋爱成果'},
        {id: 5, name: '踩坑记录', desc: '将你遇到的坑分享给大家'},
    ]
    const editor = reactive({
        type: null,
        title: '',
        text: ''
    })
    const emit = defineEmits(['close'])
</script>

<style scoped>
    :deep(.el-drawer) {
        width: 70%;
        margin: auto;
        border-radius: 5px 5px 0 0;
    }

    :deep(.el-drawer__header) {
        margin: 0;
    }
    :deep(.ql-toolbar.ql-snow ){
        border-radius: 5px 5px 0 0 ;
    }
    :deep(.ql-editor){
        font-size: 14px;
    }
</style>