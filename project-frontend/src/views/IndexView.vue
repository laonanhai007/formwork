<template>
    <div>主页</div>
    <div>你好{{ store.auth.user != null ? store.auth.user.username : '' }}</div>
    <el-button type="danger" @click="logout">退出登录</el-button>
</template>

<script setup>
    import {post} from "@/net";
    import {ElMessage} from "element-plus";
    import router from "@/router";
    import {useStore} from "@/stores";

    const store = useStore()
    const logout = () => {
        post('/api/auth/logout', null, (msg, code) => {
            store.auth.user = null
            ElMessage.success(msg)
            router.push('/')
        })
    }
</script>

<style scoped>

</style>