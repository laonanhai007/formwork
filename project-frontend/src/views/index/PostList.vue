<template>
    <div style="display: flex;margin: 20px auto;gap: 20px;max-width: 900px">
        <div style="flex: 1">
            <LightCard>
                <div class="create-topic" @click="showEditor = !showEditor">
                    <el-icon style="margin-top: -10px">
                        <EditPen/>
                    </el-icon>
                    点击发表帖子...
                </div>
            </LightCard>
            <LightCard style="margin-top: 10px;display: flex;gap: 8px">
                <div @click="type = item.id" :class="`type-select-card ${type === item.id?'active':''}`"
                     v-for="item in store.forum.types">
                    <ColorDot :color="item.color"/>
                    <span style="margin-left: 5px">{{ item.name }}</span>
                </div>
            </LightCard>
            <transition name="el-fade-in" mode="out-in">
                <div v-if="list?.length">
                    <div style="margin-top: 10px" v-infinite-scroll="updateList">
                        <LightCard style="margin-top: 10px" v-for="item in list" class="topic-card"
                                   @click="router.push('/index/topic-detail/'+item.id)">
                            <div style="display: flex">
                                <div>
                                    <el-avatar class="avatar" size="middle"
                                               src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                                    />
                                </div>
                                <div style="margin-left: 7px">
                                    <div style="font-size: 14px;margin-top: 10px">用户名:{{ item.username }}</div>
                                </div>
                            </div>
                            <div>
                                <div class="topic-type">{{ typeNames[item.type - 1] }}</div>
                                <span style="font-weight: bold;margin-left: 7px"> {{ item.title }}</span>
                            </div>
                            <div class="topic-content">{{ item.content }}</div>
                            <div style="display: flex;gap:20px;font-size:13px;margin-top: 10px">
                                <div>
                                    <el-icon>
                                        <Pointer/>
                                    </el-icon>
                                    {{ item.like }}点赞
                                </div>
                                <div>
                                    <el-icon>
                                        <Star/>
                                    </el-icon>
                                    {{ item.collect }}收藏
                                </div>
                            </div>
                        </LightCard>
                    </div>
                </div>
            </transition>


        </div>
        <div style="width: 280px;">
            <div style="position: sticky;top: 20px">
                <LightCard class="collect-list-button" @click="collects = true">
                    <span><el-icon><FolderOpened/></el-icon> 查看我的收藏</span>
                    <el-icon style="transform: translateY(3px)"><ArrowRightBold/></el-icon>
                </LightCard>
                <LightCard style="margin-top: 10px">
                    <div style="text-align: center;">
                        <div style="font-weight: bold;font-size: 25px">论坛公告</div>
                    </div>
                    <el-divider style="margin: 8px"/>
                    <div>
                        <div style="font-size: 13px;color: gray">公告内容河南山东i过筛纳斯哦给你扫地阿斯顿平方米松马上
                        </div>
                    </div>
                </LightCard>
            </div>
        </div>
        <TopicEditor :show="showEditor" @success="showEditor = false" @close="showEditor = !showEditor"/>
        <TopicCollectList :show="collects" @close="collects = false"></TopicCollectList>
    </div>
</template>

<script setup>
    import LightCard from "@/components/LightCard.vue";
    import {ArrowLeftBold, ArrowRightBold, EditPen, FolderOpened, Pointer, Star} from "@element-plus/icons-vue";
    import TopicEditor from "@/components/TopicEditor.vue";
    import {ref, watch} from "vue";
    import {get, post} from "@/net";
    import {useStore} from "@/stores";
    import ColorDot from "@/components/ColorDot.vue";
    import router from "@/router";
    import TopicCollectList from "@/components/TopicCollectList.vue";

    // const topics = reactive({
    //     list:[],
    //     type:0,
    //     page:0
    // })

    const type = ref(0)
    const list = ref(null)
    post('/api/forum/list-topic', {
        page: 0,
        type: 0
    }, (message) => {
        list.value = message
    })
    const store = useStore()
    const typeNames = ['日常闲聊', '真诚交友', '问题反馈', '恋爱官宣', '踩坑记录']
    get('/api/forum/types', (message) => {
        const array = []
        array.push({name: '全部', id: 0, color: 'linear-gradient(45deg,white,red,orange,gold,green,blue)'})
        message.forEach(d => array.push(d))
        store.forum.types = array
    })
    const showEditor = ref(false)

    watch(type, () => {
        list.value = null
        updateList()
    })

    function updateList() {
        post('/api/forum/list-topic?page=0&type=' + type.value + '', null, (message) => {
            list.value = message
        })
    }

    const collects = ref(false)
</script>

<style scoped>
    .create-topic {
        padding: 0 20px;
        background-color: #cecdcd;
        border-radius: 5px;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        color: gray;

        &:hover {
            cursor: pointer;
        }
    }

    .topic-card {
        transition: scale .3s;

        &:hover {
            scale: 1.05;
            cursor: pointer;
        }

        .topic-content {
            font-size: 13px;
            color: gray;
            margin: 10px 0;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .topic-type {
            display: inline-block;
            border: solid 0.5px gray;
            border-radius: 3px;
            font-size: 12px;
            padding: 0 5px;
        }

    }

    .type-select-card {
        background-color: #f5f5f5;
        padding: 2px 7px;
        font-size: 14px;
        border-radius: 3px;
        box-sizing: border-box;
        transition: background-color .3s;

        &.active {
            border: solid 1px gray;
        }

        &:hover {
            cursor: pointer;
            background-color: #dadada;
        }
    }

    .collect-list-button{
        font-size: 14px;
        display: flex;
        justify-content: space-between;
        transition: color 0.3s;

        &:hover{
            cursor: pointer;
            opacity: 0.6;
        }
    }

</style>