<template>
    <div style="display: flex;margin: 20px auto;gap: 20px;max-width: 900px">
        <div style="flex: 1">
            <LightCard>
                <div class="create-topic" @click="showEditor = !showEditor">
                    <el-icon style="margin-top: -10px">
                        <EditPen/>
                    </el-icon>点击发表帖子...
                </div>
            </LightCard>
            <LightCard style="margin-top: 10px;height: 40px"></LightCard>
            <div style="margin-top: 10px">
                <LightCard style="height: 100px;margin-top: 10px" v-for="item in 10">

                </LightCard>

            </div>
        </div>
        <div style="width: 280px;">
            <div style="position: sticky;top: 20px">
                <LightCard>
                    <div style="text-align: center">
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
        <TopicEditor :show="showEditor" @close="showEditor = !showEditor"/>
    </div>
</template>

<script setup>
    import LightCard from "@/components/LightCard.vue";
    import {EditPen} from "@element-plus/icons-vue";
    import TopicEditor from "@/components/TopicEditor.vue";
    import {ref} from "vue";

    const showEditor = ref(false)
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
        &:hover{
            cursor: pointer;
        }
    }
</style>