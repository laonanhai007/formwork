<template>
    <div class="topic-page" v-if="topic.data">
        <div class="topic-main" style="position: sticky;top: 0;z-index: 10">
            <div style="display: flex;width: 100%;border-radius: 3px;border: solid 1px gray">
                <el-button :icon="ArrowLeft" type="info" round plain @click="router.push('/index')">返回列表</el-button>
                <div style="margin: auto;font-weight: bold">{{topic.data.title}}</div>
            </div>
        </div>
        <div class="topic-main">
            <div class="topic-main-left">
                <div>
                    <el-avatar :size="60"
                               src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    />
                </div>
                <div style="font-size: 18px;font-weight: bold">
                    <span>用户名</span>
                    <span><el-icon><Male/></el-icon></span>
                    <div class="desc">邮箱</div>
                </div>
                <el-divider style="margin: 10px 0"/>
                <div style="text-align: left;margin-left: 10px" class="desc">
                    <div>QQ:{{ topic.data.user.qq }}</div>
                    <div>微信:{{ topic.data.user.wx }}</div>
                    <div>电话号码:{{ topic.data.user.phone }}</div>
                </div>
                <el-divider style="margin: 10px 0"/>
                <div class="desc">
                    <div>{{ topic.data.user.desc }}</div>
                </div>
            </div>
            <div class="topic-main-right">
                <div class="topic-content" v-html="content">
                </div>
            </div>
        </div>
        <div></div>
    </div>
</template>

<script setup>
    import {useRoute} from "vue-router";
    import {reactive} from "vue";
    import {get} from "@/net";
    import {ArrowLeft, Male} from "@element-plus/icons-vue";
    import {QuillDeltaToHtmlConverter} from 'quill-delta-to-html'
    import {computed} from "vue";
    import router from "@/router";
    const route = useRoute()
    const tid = route.params.tid

    get(`/api/forum/topic?tid=${tid}`, data => {
        console.log(data)
        topic.data = data
    })

    const topic = reactive({
        data: null,
        comments: []
    })
    const  content = computed(() => {
        const ops = JSON.parse(topic.data.content).ops
        const converter = new QuillDeltaToHtmlConverter(ops, {inlineStyles: true})
        return converter.convert()
    })
</script>

<style scoped>
    .topic-page {
        display: flex;
        flex-direction: column;
        gap: 10px;
        padding: 10px 0;
    }

    .topic-main {
        display: flex;
        border-radius: 7px;
        margin: 0 auto;
        background-color: var(--el-bg-color);
        width: 800px;

        .topic-main-left {
            text-align: center;
            width: 200px;
            padding: 10px;
            border-right: solid 1px var(--el-border-color);

            .desc {
                font-size: 12px;
                color: gray;
            }
        }

        .topic-main-right {
            width: 600px;
            padding: 10px 20px;
            .topic-content{
                font-size: 14px;
                line-height: 22px;
                opacity: 0.8;
            }
        }
    }
</style>