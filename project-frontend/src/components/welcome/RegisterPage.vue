<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 100px">
            <div style="font-size: 25px;font-weight: bold">注册</div>
            <div style="font-size: 14px;color: gray;margin-top: 10px">填写内容来完成注册</div>
        </div>
        <el-form style="margin-top: 40px"
                 :model="form"
                 :rules="rules"
                 @validate="myValidate"
                 ref="formRef"
        >
            <el-form-item prop="username">
                <el-input type="text" placeholder="用户名" v-model="form.username">
                    <template #prefix>
                        <el-icon>
                            <User/>
                        </el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" placeholder="密码" v-model="form.password">
                    <template #prefix>
                        <el-icon>
                            <Lock/>
                        </el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item prop="passwordRepeat">
                <el-input type="password" placeholder="确认密码" v-model="form.passwordRepeat">
                    <template #prefix>
                        <el-icon>
                            <Lock/>
                        </el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input type="text" placeholder="邮箱" v-model="form.email">
                    <template #prefix>
                        <el-icon>
                            <Message/>
                        </el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item prop="code">
                <el-row style="width: 100%;">
                    <el-col :span="16">
                        <el-input type="text" placeholder="邮箱验证码" v-model.number="form.code">
                            <template #prefix>
                                <el-icon>
                                    <Check/>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-col>
                    <el-col :span="8" style="text-align: right">
                        <el-button @click="validEmail" type="success" :disabled="!isEmail">获取验证码</el-button>
                    </el-col>
                </el-row>
            </el-form-item>

        </el-form>
        <div style="margin-top: 60px">
            <el-button style="width: 200px;font-size: 16px" type="warning" @click="register">
                立即注册
            </el-button>
        </div>
        <el-divider style="margin-top: 30px">
            <span style="color: gray">已有账号?</span>
        </el-divider>
        <div style="margin-top: 10px">
            <el-button style="width: 200px;font-size: 16px" type="primary" @click="router.push('/')">立即登录
            </el-button>
        </div>


    </div>
</template>

<script setup>
    import {Check, Lock, Message, User} from "@element-plus/icons-vue";
    import router from "@/router";
    import {ref, reactive} from "vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";

    const form = ref({
        username: '',
        password: '',
        passwordRepeat: '',
        email: '',
        code: ''
    })
    const validateUsername = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('用户名不能为空'))
        } else if (value.length > 20 || value.length < 3) {
            callback(new Error('用户名长度为3~20位'))
        } else if (!/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/.test(value)) {
            callback(new Error('用户名不能包含特殊字符'))
        } else {
            callback()
        }
    }

    const validatePassword = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('密码不能为空'))
        } else if (value.length > 20 || value.length < 6) {
            callback(new Error('密码长度为6~20位'))
        } else {
            callback()
        }
    }

    const validatePasswordRepeat = (rule, value, callback) => {
        if (value !== form.value.password) {
            callback(new Error('密码不一致'))
        } else {
            callback()
        }
    }
    const rules = reactive({
        username: [{validator: validateUsername, trigger: ['blur', 'change']}],
        password: [{validator: validatePassword, trigger: ['blur', 'change']}],
        passwordRepeat: [
            {validator: validatePasswordRepeat, trigger: ['blur', 'change']}
        ],
        email: [
            {required: true, message: '请填写邮箱'},
            {type: 'email', message: '请填写正确邮箱格式'}
        ],
        code: [
            {required: true, message: '请填写验证码'},
            {type: 'number', message: '验证码必须为数字'}
        ]

    })

    const isEmail = ref(false)
    const myValidate = (prop, isValid) => {
        if (prop === 'email') {
            isEmail.value = isValid
        }
    }
    const formRef = ref()
    const register = () => {
        formRef.value.validate((valid) => {
            if (valid) {
                post('/api/auth/register',form.value,(message)=>{
                    ElMessage.success(message)
                    router.push("/")

                })
            } else {
                return false
            }
        })
    }

    const validEmail = ()=>{
        post('/api/auth/valid-register-email',{
            email:form.value.email
        },(message)=>{
            ElMessage.success(message)
        })
    }
</script>

<style scoped>

</style>