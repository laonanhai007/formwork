<template>
    <div style="display:flex;padding: 20px 30px">
        <div style="flex: 1;">
            <el-tabs v-model="activeName" class="demo-tabs">
                <el-tab-pane label="个人信息设置" name="info">
                    <InfoSettings/>
                </el-tab-pane>
                <el-tab-pane label="账户安全设置" name="security">
                    <SecuritySettings/>
                </el-tab-pane>
                <el-tab-pane label="隐私设置" name="privacy">
                    <PrivacySettings/>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div style="width: 300px">
            <div class="user-card">
                <el-row style="text-align: right">
                    <el-col :span="16" style="margin: auto">
                        <div style="font-weight: bold">
                            {{ store.auth.user.username }}
                        </div>
                        <div style="font-size: 13px">
                            {{ store.auth.user.email }}
                        </div>
                    </el-col>
                    <el-col :span="8" style="text-align: center">
                        <el-avatar class="avatar" :size="70" style="margin-top: 10px"
                                   src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                        />
                        <el-upload
                                action=""
                                :http-request="uploadFile"
                                :show-file-list="false"
                                :before-upload="beforeAvatarUpload">
                            <el-button disabled type="default" size="small" round>修改头像</el-button>
                        </el-upload>
                    </el-col>
                </el-row>
                <el-divider style="margin: 4px 0"/>

            </div>
        </div>
    </div>

</template>

<script setup>
    import {ref, reactive} from "vue";
    import {useStore} from "@/stores";
    import InfoSettings from "@/components/settings/InfoSettings.vue";
    import SecuritySettings from "@/components/settings/SecuritySettings.vue";
    import PrivacySettings from "@/components/settings/PrivacySettings.vue";
    import {ElMessage} from "element-plus";
    import axios from "axios";
    import {post, postFile} from "@/net";

    const activeName = ref('info')

    const store = useStore()

    function beforeAvatarUpload(rawFile) {
        if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
            ElMessage.warning('图像只能为jpg/png格式')
        } else if (rawFile.size / 1024 / 1024 > 2) {
            ElMessage.error('图像大小不能大于2m')
        } else return true
    }

    const uploadFile = (item) => {
        postFile('/api/image/avatar', {
            file: item.file
        }, () => {
            ElMessage.success('文件上传成功')
        })
    }
</script>

<style scoped>
    .user-card {
        width: 260px;
        height: 300px;
        margin: auto;
        border-radius: 5px;
        border: solid 1px lightgray;
    }
</style>