<script setup>
    import {ElMessage} from "element-plus";
    import {useStore} from "@/stores";
    import {post} from "@/net";
    import router from "@/router";

    const store = useStore()

    if (store.auth.user == null) {
        post('/api/user/me', null, (message) => {
            store.auth.user = message
            ElMessage.success('已登录');
            router.push('/index')
        }, () => {
            ElMessage.warning('未登录')
            store.auth.user = null
        })
    }

</script>

<template>
    <router-view/>
</template>

<style scoped>

</style>
