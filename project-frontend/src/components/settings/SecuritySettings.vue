<template>
    <div>
        <h1>
            <el-icon>
                <Message/>
            </el-icon>
            邮箱设置
        </h1>
        <el-form
                label-position="top"
                label-width="100px"
                :model="securityForm"
                style="max-width: 460px"
        >
            <el-form-item label="新邮箱">
                <el-input v-model="securityForm.newEmail"/>
            </el-form-item>
        </el-form>
        <el-button type="success" :icon="Select">重置邮箱</el-button>

    </div>
    <div>
        <h1>
            <el-icon>
                <Lock/>
            </el-icon>
            密码设置
        </h1>
        <el-form
                label-position="top"
                label-width="100px"
                :model="securityForm"
                style="max-width: 460px"
        >
            <el-form-item label="原先密码">
                <el-input v-model="securityForm.oldPassword"/>
            </el-form-item>
            <el-form-item label="新密码">
                <el-input v-model="securityForm.newPassword"/>
            </el-form-item>
            <el-form-item label="重复密码">
                <el-input v-model="securityForm.repeatPassword"/>
            </el-form-item>
            <el-button type="success" :icon="Select">重置密码</el-button>
        </el-form>
    </div>
</template>

<script setup>
    import {ref} from "vue";
    import {Lock, Message, Select} from "@element-plus/icons-vue";

    const securityForm = ref({
        newEmail: '',
        oldPassword: '',
        newPassword: '',
        repeatPassword: ''
    })
</script>

<style scoped>

</style>