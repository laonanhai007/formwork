<template>
    <div>
        <el-checkbox v-model="privacyForm.email" label="在主页展示我的邮箱" size="large"/>
    </div>
    <div>
        <el-checkbox v-model="privacyForm.sex" label="在主页展示我的性别" size="large"/>
    </div>
    <div>
        <el-checkbox v-model="privacyForm.phone" label="在主页展示我的电话" size="large"/>
    </div>
    <div>
        <el-checkbox v-model="privacyForm.qq" label="在主页展示我的qq账号" size="large"/>
    </div>
    <div>
        <el-checkbox v-model="privacyForm.wx" label="在主页展示我的微信账号" size="large"/>
    </div>
    <div>
        <el-checkbox v-model="privacyForm.blog" label="在主页展示我的博客地址" size="large"/>
    </div>
    <el-button type="success" :icon="Select">保存个人隐私设置</el-button>

</template>

<script setup>
    import {reactive} from "vue";
    import {Select} from "@element-plus/icons-vue";

    const privacyForm = reactive({
        email: false,
        qq: false,
        wx: false,
        blog: false,
        sex:false,
        phone:false
    })
</script>

<style scoped>

</style>