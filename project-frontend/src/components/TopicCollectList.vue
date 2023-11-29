<template>
    <el-drawer :model-value="show" @close="emit('close')" @open="init" title="我的帖子收藏列表">
        <div class="collect-list">
            <LightCard v-for="(item,index) in list" class="topic-card" @click="router.push('/index/topic-detail/'+item.id)">
                <div>
                    <b>{{ item.title }}</b>
                </div>
                <el-link type="danger" @click.stop="deleteCollect(index,item.id)">取消收藏</el-link>
            </LightCard>
        </div>
    </el-drawer>
</template>

<script setup>
    import {get} from "@/net";
    import {ref} from "vue";
    import LightCard from "@/components/LightCard.vue";
    import router from "@/router";
    import {ElMessage} from "element-plus";

    defineProps(
        {
            show: Boolean
        }
    )
    const list = ref([])
    const emit = defineEmits(['close'])

    function init() {
        get('/api/forum/collect', (data) => {
            list.value = data
        })
    }

    function deleteCollect(index,tid){
        get(`/api/forum/cancel-collect?tid=${tid}`,()=>{
            ElMessage.success('已取消收藏')
            list.value.splice(index,1)
        })
    }
</script>

<style scoped>
    .collect-list {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .topic-card {
        background-color: rgba(128, 128, 128, 0.2);
        transition: 0.3s;
        display: flex;
        justify-content: space-between;

        .title{
            font-size: 14px;
            flex: 1;
        }

        &:hover{
            cursor: pointer;
            scale: 1.04;
        }
    }
</style>