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
                    <el-select placeholder="选择帖子类型..." v-model="editor.type" :disabled="!editor.types.length">
                        <el-option v-for="item in editor.types" :value="item.id" :label="item.name"/>
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
    import {post} from "@/net";

    defineProps({
        show: Boolean
    })
    const editor = reactive({
        type: null,
        title: '',
        text: '',
        types: []
    })
    post('/api/forum/types', null,(message) => {
        editor.types = message
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

    :deep(.ql-toolbar.ql-snow ) {
        border-radius: 5px 5px 0 0;
    }

    :deep(.ql-editor) {
        font-size: 14px;
    }
</style>