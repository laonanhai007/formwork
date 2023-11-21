<template>
    <div>主页</div>
    <el-button type="danger" @click="logout">退出登录</el-button>
</template>

<script setup>
import {get} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";

const logout = () => {
    get('/api/auth/logout', (msg, code) => {
        ElMessage.success(msg)
        router.push('/')
    })
}
</script>

<style scoped>

</style>