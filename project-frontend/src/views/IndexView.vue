<template>
    <div style="height: 100vh">
        <el-container style="height: 100vh">
            <el-aside width="250px" style="border-right: solid 1px #c2c2c2;text-align: center">
                <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
                    <el-radio-button :label="false">expand</el-radio-button>
                    <el-radio-button :label="true">collapse</el-radio-button>
                </el-radio-group>
                <el-menu
                        style=""
                        :collapse="isCollapse"
                        @open="handleOpen"
                        @close="handleClose"
                >

                    <el-menu-item index="1">
                        <el-icon><icon-menu/></el-icon>
                        <template #title>帖子列表</template>
                    </el-menu-item>
                    <el-menu-item index="2">
                        <el-icon><document/></el-icon>
                        <template #title>表白墙</template>
                    </el-menu-item>
                    <el-menu-item index="3">
                        <el-icon><setting/></el-icon>
                        <template #title>帖子管理</template>
                    </el-menu-item>
                    <el-menu-item index="4">
                        <el-icon><setting/></el-icon>
                        <template #title>我的收藏</template>
                    </el-menu-item>
                    <el-menu-item index="5">
                        <el-icon><setting/></el-icon>
                        <template #title>个人设置</template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header style="border-bottom: solid 1px #c2c2c2">
                    <el-row>
                        <el-col :span="14" style="margin: auto">
                            <el-input
                                    style="width: 400px"
                                    v-model="search"
                                    placeholder="搜索论坛内容"
                            >
                                <template #prefix>
                                    <el-icon>
                                        <Search/>
                                    </el-icon>
                                </template>
                                <template #append>
                                    <el-select v-model="select" placeholder="Select" style="width: 115px">
                                        <el-option label="Restaurant" value="1"/>
                                        <el-option label="Order No." value="2"/>
                                        <el-option label="Tel" value="3"/>
                                    </el-select>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="9" style="margin: auto">
                            <div style="text-align: right;margin-right: 10px">
                                <div style="font-weight: bold">
                                    {{ store.auth.user != null ? store.auth.user.username : '' }}
                                </div>
                                <div style="color: gray;font-size: 13px">
                                    {{ store.auth.user != null ? store.auth.user.email : '' }}
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="1">
                            <el-dropdown trigger="click">
                                <span class="el-dropdown-link">
                                    <el-avatar class="avatar" size="large"
                                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                                    />
                                </span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item :icon="CirclePlus">Action 3</el-dropdown-item>
                                        <el-dropdown-item :icon="Check">个人设置</el-dropdown-item>
                                        <el-dropdown-item  :icon="SwitchButton" @click="logout" divided>
                                            <div style="color: red">退出登录</div>
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </el-col>
                    </el-row>

                </el-header>
                <el-main style="">Main
                    <div>主页</div>

                    <el-button type="danger" @click="logout">退出登录</el-button>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
    import {post} from "@/net";
    import {ElMessage} from "element-plus";
    import router from "@/router";
    import {useStore} from "@/stores";
    import {
        Document,
        Location,
        Setting,
        Menu as IconMenu,
        Search,
        CirclePlus,
        Check,
        CircleCheck, SwitchButton,
    } from "@element-plus/icons-vue";
    import {ref} from "vue";

    const store = useStore()
    const logout = () => {
        post('/api/auth/logout', null, (msg, code) => {
            store.auth.user = null
            ElMessage.success(msg)
            router.push('/')
        })
    }


    const isCollapse = ref(true)
    const handleOpen = (key, keyPath) => {
        console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
        console.log(key, keyPath)
    }

    const search = ref('')

</script>

<style scoped>

.avatar:hover{
    cursor: pointer;
}
</style>