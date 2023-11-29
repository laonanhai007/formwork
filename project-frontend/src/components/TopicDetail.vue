<template>
    <div class="topic-page" v-if="topic.data">
        <div class="topic-main" style="position: sticky;top: 0;z-index: 10">
            <div style="display: flex;width: 100%;border-radius: 3px;border: solid 1px gray">
                <el-button :icon="ArrowLeft" type="info" round plain @click="router.push('/index')">返回列表</el-button>
                <div style="margin: auto;font-weight: bold">{{ topic.data.title }}</div>
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
                <div style="text-align: right;margin-top: 30px">
                    <InteractButton style="margin-right: 15px" name="编辑帖子"
                                    :check="false"
                                    color="dodgerblue"
                                    v-if="store.auth.user.id === topic.data.user.uid"
                                    @check="edit = true">
                        <el-icon>
                            <EditPen/>
                        </el-icon>
                    </InteractButton>
                    <InteractButton style="margin-right: 5px" check-name="已点赞" name="点个赞吧!" color="pink"
                                    :check="topic.like"
                                    @check="interact('like','点赞')">
                        <el-icon>
                            <Pointer/>
                        </el-icon>
                    </InteractButton>
                    <InteractButton
                            @check="interact('collect','收藏')"
                            name="收藏" check-name="已收藏" color="orange" :check="topic.collect">
                        <el-icon>
                            <Star/>
                        </el-icon>
                    </InteractButton>
                </div>
            </div>
        </div>
        <TopicEditor
                :default-type="topic.data.type"
                :default-title="topic.data.title"
                :default-text="topic.data.content"
                :tid="topic.data.id"
                :show="edit" @close="edit = false"/>
        <div class="add-comment"  @click="comment.show = true">
            <el-icon>
                <Plus />
            </el-icon>
        </div>
        <TopicCommentEditor :show="comment.show"
                            @close="comment.show = false"
                            :tid="topic.data.id"
                            :quote="comment.quote"/>
    </div>

</template>

<script setup>
    import {useRoute} from "vue-router";
    import {reactive, ref} from "vue";
    import {get} from "@/net";
    import {ArrowLeft, EditPen, Male, Plus, Pointer, Star} from "@element-plus/icons-vue";
    import {QuillDeltaToHtmlConverter} from 'quill-delta-to-html'
    import {computed} from "vue";
    import router from "@/router";
    import InteractButton from "@/components/InteractButton.vue";
    import {ElMessage} from "element-plus";
    import {useStore} from "@/stores";
    import TopicEditor from "@/components/TopicEditor.vue";
    import TopicCommentEditor from "@/components/TopicCommentEditor.vue";

    const edit = ref(false)
    const route = useRoute()
    const tid = route.params.tid
    const store = useStore()
    get(`/api/forum/topic?tid=${tid}`, data => {
        console.log(data)
        topic.data = data
        topic.like = data.interact.like
        topic.collect = data.interact.collect
    })

    const topic = reactive({
        data: null,
        comments: [],
        like: false,
        collect: false
    })
    const comment = reactive({
        show:false,
        text:'',
        quote: -1
    })
    const content = computed(() => {
        const ops = JSON.parse(topic.data.content).ops
        const converter = new QuillDeltaToHtmlConverter(ops, {inlineStyles: true})
        return converter.convert()
    })

    const interact = (type, message) => {
        get(`/api/forum/interact?tid=${topic.data.id}&type=${type}&state=${!topic[type]}`, () => {
            topic[type] = !topic[type]
            if (topic[type])
                ElMessage.success(message + '成功')
            else
                ElMessage.success('已取消' + message)
        })

    }
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

            .topic-content {
                font-size: 14px;
                line-height: 22px;
                opacity: 0.8;
            }
        }
    }

    .add-comment {
        position: fixed;
        bottom: 20px;
        right: 20cap;
        width: 40px;
        height: 40px;
        border-radius: 50%;
        color: blue;
        font-size: 18px;
        text-align: center;
        line-height: 45px;
        background-color: var(--el-bg-color-overlay);
        box-shadow: var(--el-box-shadow-light);

        &:hover {
            cursor: pointer;
            background: var(--el-border-color-extra-light);
        }
    }
</style>