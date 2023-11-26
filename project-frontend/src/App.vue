<script setup>
    import {useStore} from "@/stores";

    const store = useStore()
    const user=  JSON.parse(localStorage.getItem("user"))
    if (user != null) {
       store.auth.user = user
    }

</script>

<template>
    <router-view/>
</template>

<style scoped>

</style>
