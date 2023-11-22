<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 120px">
            <div style="font-size: 25px;font-weight: bold">注册</div>
            <div style="font-size: 14px;color: gray;margin-top: 10px">填写内容来完成注册</div>
        </div>
        <div style="margin-top: 40px">
            <el-input type="text" placeholder="用户名" v-model="form.username">
                <template #prefix>
                    <el-icon>
                        <User/>
                    </el-icon>
                </template>
            </el-input>
            <el-input type="password" placeholder="密码" style="margin-top: 10px" v-model="form.password">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
            <el-input type="password" placeholder="确认密码" style="margin-top: 10px" v-model="form.passwordRepeat">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
            <el-input type="text" placeholder="邮箱" style="margin-top: 10px" v-model="form.email">
                <template #prefix>
                    <el-icon>
                        <Message/>
                    </el-icon>
                </template>
            </el-input>
            <div style="margin-top: 12px">
                <el-row>
                    <el-col :span="16">
                        <el-input type="text" placeholder="邮箱验证码" v-model="form.code">
                            <template #prefix>
                                <el-icon>
                                    <Check/>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-col>
                    <el-col :span="8" style="text-align: right">
                        <el-button type="success">获取验证码</el-button>
                    </el-col>
                </el-row>
            </div>


            <div style="margin-top: 60px">
                <el-button style="width: 200px;font-size: 16px" type="warning" @click="router.push('/register')">
                    立即注册
                </el-button>
            </div>
            <el-divider style="margin-top: 30px">
                <span style="color: gray">已有账号?</span>
            </el-divider>
            <div style="margin-top: 10px">
                <el-button  style="width: 200px;font-size: 16px" type="primary" @click="router.push('/')">立即登录
                </el-button>
            </div>
        </div>

    </div>
</template>

<script setup>
    import {Check, Lock, Message, User} from "@element-plus/icons-vue";
    import router from "@/router";
    import {ref} from "vue";

    const form = ref({
        username: '',
        password: '',
        passwordRepeat: '',
        email: '',
        code: ''
    })

</script>

<style scoped>

</style>