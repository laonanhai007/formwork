<template>
    <el-form
            label-position="top"
            label-width="100px"
            :model="infoForm"
            style="max-width: 460px"
    >
        <el-form-item label="用户名">
            <el-input v-model="infoForm.username"/>
        </el-form-item>
        <el-form-item label="性别">
            <el-radio-group v-model="infoForm.sex">
                <el-radio label="male" size="large">男</el-radio>
                <el-radio label="female" size="large">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="QQ">
            <el-input v-model="infoForm.qq"/>
        </el-form-item>
        <el-form-item label="微信">
            <el-input v-model="infoForm.wx"/>
        </el-form-item>
        <el-form-item label="博客">
            <el-input v-model="infoForm.blog"/>
        </el-form-item>
        <el-form-item label="电话">
            <el-input v-model="infoForm.phone"/>
        </el-form-item>
        <el-form-item label="个人简介">
            <el-input type="textarea" rows="5" v-model="infoForm.desc"/>
        </el-form-item>
    </el-form>
    <el-button type="success" :icon="Select" @click="saveInfo">保存个人信息设置</el-button>
    <el-text style="font-size: 13px;color: lightgray;margin-left: 5px">未填空的内容将不会更改</el-text>
</template>

<script setup>
    import {reactive} from "vue";
    import {Select} from "@element-plus/icons-vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";
    import {useStore} from "@/stores";

    const store = useStore()
    const infoForm = reactive({
        uid: store.auth.user.id,
        username: '',
        qq: '',
        wx: '',
        blog: '',
        phone: '',
        desc: ''

    })

    const saveInfo = () => {
        post('/api/user/save-info', {
            uid: store.auth.user.id,
            sex: infoForm.sex,
            qq: infoForm.qq,
            wx: infoForm.wx,
            blog: infoForm.blog,
            phone: infoForm.phone,
            desc: infoForm.desc
        }, (message) => {
            ElMessage.success(message)
        }, 'json')
    }
</script>

<style scoped>

</style>