<template>
    <div>
        <el-drawer
                :model-value="show"
                @close="emit('close')"
                direction="btt" :size="270"
                :close-on-click-modal="false"
                title="发表评论">
            <div >
                <div style="flex: 1;">
                    <el-input placeholder="请发表友善评论" v-model="commentContent"/>
<!--                    <quill-editor style="height: 120px" v-model:content="content"-->
<!--                    placeholder="请发表友善评论"/>-->
                </div>
                <div style="text-align: right;margin-top: 10px">
                    <el-button type="success" plain @click="submitComment">发表评论</el-button>
                </div>
            </div>
        </el-drawer>
    </div>
</template>

<script setup>
    import {Delta, QuillEditor} from '@vueup/vue-quill'
    import '@vueup/vue-quill/dist/vue-quill.snow.css';
    import {ref} from "vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";

    const props = defineProps({
        show: Boolean,
        tid:String,
        quote:Number
    })
    const commentContent = ref('')
    const content = ref()
    const emit = defineEmits(['close'])

    const submitComment = ()=>{
        post('/api/forum/add-comment',{
            tid:props.tid,
            quote:props.quote,
            // content:JSON.stringify(content.value)
            content:commentContent.value
        },()=>{
            ElMessage.success('发表评论成功')
            content.value = null
            emit('close')
        },'json')

    }
</script>

<style scoped>
    :deep(.el-drawer) {
        width: 70%;
        margin: 20px auto;
        border-radius: 5px;
    }

    :deep(.el-drawer__header) {
        margin: 0;
    }

    :deep(.el-drawer__body) {
        padding: 10px;
    }

    :deep(.ql-toolbar.ql-snow ) {
        border-radius: 5px 5px 0 0;
    }

    :deep(.ql-editor) {
        font-size: 14px;
    }
</style>