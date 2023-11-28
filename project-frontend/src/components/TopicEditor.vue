<template>
    <div>
        <el-drawer :model-value="show"
                   direction="btt"
                   :size="700"
                   :close-on-click-modal="false"
                   @open="initEditor"
                   @close="emit('close')">
            <template #header>
                <div style="font-weight: bold">发表新的帖子</div>
            </template>
            <div style="display: flex;gap: 10px">
                <div style="width: 140px;">
                    <el-select placeholder="选择帖子类型..." v-model="editor.type"
                               :disabled="!store.forum.types.length">
                        <el-option
                                @click="showInfo(item)"
                                v-for="item in store.forum.types"
                                :value="item.id" :label="item.name"
                                :style="{color: item.color}">
                        </el-option>
                    </el-select>
                </div>
                <div style="flex: 1">
                    <el-input placeholder="帖子标题..." :prefix-icon="Document" v-model="editor.title"/>
                </div>
            </div>
            <div style="margin-top: 5px">
                <ColorDot :color="simpleShow.color"/>
                <span style="font-size: 13px;color: gray;margin-left: 5px">
                    {{ simpleShow.desc }}
                </span>
            </div>

            <div style="margin-top: 15px ">
                <quill-editor v-model:content="editor.text"
                              style="height: 350px;border-radius: 0 0 5px 5px"
                              ref="refEditor"
                              placeholder="开始编辑"/>
            </div>
            <el-row style="margin-top: 10px">
                <el-col :span="12">
                    <div style="font-size: 13px;color: gray">当前字数 {{ contentLength }} (最大支持10000字)</div>
                </el-col>
                <el-col :span="12" style="text-align: right">
                    <el-button type="success" plain :icon="Position" @click="submitTopic">立即发布</el-button>
                </el-col>
            </el-row>
        </el-drawer>
    </div>
</template>

<script setup>
    import {QuillEditor} from '@vueup/vue-quill'
    import '@vueup/vue-quill/dist/vue-quill.snow.css';
    import {Document, Position} from "@element-plus/icons-vue";
    import {reactive, computed, ref} from "vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";
    import ColorDot from "@/components/ColorDot.vue";
    import {useStore} from "@/stores";

    defineProps({
        show: Boolean
    })
    const editor = reactive({
        type: null,
        title: '',
        text: '',
    })
    const store = useStore()


    const emit = defineEmits(['close', 'success'])

    function deltaToText(delta) {
        if (!delta.ops) {
            return ""
        }
        let str = ""
        for (let op of delta.ops) {
            str += op.insert
        }
        return str.replace(/\s/g, "")
    }

    function submitTopic() {
        console.log(editor.text)
        post('/api/forum/create-topic',
            {
                type: editor.type,
                title: editor.title,
                content: editor.text
            }, (msg) => {
                ElMessage.success(msg);
                emit('success')
            }, 'json')
    }

    const refEditor = ref()

    function initEditor() {
        refEditor.value.setContents('', 'user')
        editor.title = ''
        editor.type = null
    }

    const contentLength = computed(() => deltaToText(editor.text).length)
    const simpleShow = ref({
        desc: '',
        color: ''
    })

    function showInfo(item) {
        simpleShow.value.desc = item.desc
        simpleShow.value.color = item.color
    }
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