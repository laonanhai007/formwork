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
                            {{store.auth.user.username}}
                        </div>
                        <div style="font-size: 13px">
                            {{store.auth.user.email}}
                        </div>
                    </el-col>
                    <el-col :span="8" style="text-align: center">
                        <el-avatar class="avatar" :size="70"
                                   src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                        />
                    </el-col>
                </el-row>
                <el-divider style="margin: 4px 0"/>
            </div>
        </div>
    </div>

</template>

<script setup>
    import {ref,reactive} from "vue";
    import {useStore} from "@/stores";
    import InfoSettings from "@/components/settings/InfoSettings.vue";
    import SecuritySettings from "@/components/settings/SecuritySettings.vue";
    import PrivacySettings from "@/components/settings/PrivacySettings.vue";
    const activeName = ref('info')

    const store = useStore()

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