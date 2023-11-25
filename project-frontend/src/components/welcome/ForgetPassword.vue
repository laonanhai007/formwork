<template>
    <div>
        <div style="margin: 30px 10px">
            <el-steps :active="active" align-center>
                <el-step title="验证邮箱"/>
                <el-step title="重置密码"/>
            </el-steps>
        </div>
        <transition name="el-fade-in-linear" >
            <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active === 0">
                <div style="margin-top: 70px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 14px;color: gray;margin-top: 10px">验证邮箱来进行密码重置</div>
                </div>
                <el-form style="margin-top: 40px"
                         :model="form"
                         :rules="rules"
                         @validate="myValidate"
                         ref="formRef"
                >

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
                    <el-button style="width: 200px;font-size: 16px" type="danger" @click="startReset">
                        开始重置密码
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
        </transition>
        <transition name="el-fade-in-linear">
            <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active === 1">
                <div style="margin-top: 70px">
                    <div style="font-size: 25px;font-weight: bold">重置密码</div>
                    <div style="font-size: 14px;color: gray;margin-top: 10px">填写新密码来完成重置</div>
                </div>
                <el-form style="margin-top: 40px"
                         :model="form"
                         :rules="rules"
                         @validate="myValidate"
                         ref="formRef2"
                >

                    <el-form-item prop="password">
                        <el-input type="password" placeholder="新密码" v-model="form.password">
                            <template #prefix>
                                <el-icon>
                                    <Lock/>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="passwordRepeat">
                        <el-input type="password" placeholder="确认新密码" v-model="form.passwordRepeat">
                            <template #prefix>
                                <el-icon>
                                    <Lock/>
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>

                </el-form>
                <div style="margin-top: 60px">
                    <el-button style="width: 200px;font-size: 16px" type="danger" @click="endReset">
                        重置密码
                    </el-button>
                </div>


            </div>
        </transition>
    </div>

</template>

<script setup>
    import {Check, Lock, Message} from "@element-plus/icons-vue";
    import router from "@/router";
    import {ref, reactive} from "vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";

    const form = ref({
        password: '',
        passwordRepeat: '',
        email: '',
        code: ''
    })


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
    const formRef2 =ref()
    const validEmail = () => {
        post('/api/auth/valid-reset-email', {
            email: form.value.email
        }, (message) => {
            ElMessage.success(message)
        })
    }

    const startReset = () =>{
        formRef.value.validate((valid) => {
            if (valid) {
                post('/api/auth/start-reset',{
                    email: form.value.email,
                    code: form.value.code
                },(message)=>{
                    active.value++
                })
            } else {
                return false
            }
        })

    }
    const endReset = ()=>{
        formRef2.value.validate((valid) => {
            if (valid) {
                post('/api/auth/end-reset',{
                    password:form.value.password
                },(message)=>{
                    ElMessage.success(message)
                    active.value = 0
                    router.push('/')
                })
            } else {
                return false
            }
        })

    }
    const active = ref(0)
</script>

<style scoped>

</style>