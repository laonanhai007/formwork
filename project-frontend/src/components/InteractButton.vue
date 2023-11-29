<template>

    <div class="interact-button">
        <span @click="emit('check')" class="icon" :style="{'color': check? color:'unset'}">
            <slot/>
        </span>
        <span class="name" :style="{'color': color}" >
            {{ check? checkName: name }}
        </span>
    </div>
</template>

<script setup>
    const emit = defineEmits(['check'])
    defineProps({
        name: String,
        color: String,
        check: Boolean,
        checkName:String
    })
</script>

<style scoped>
    .interact-button {
        display: inline-block;
        height: 20px;

        .name {
            font-size: 13px;
            margin-left: 5px;
            opacity: 0.7;
        }

        .icon {
            vertical-align: center;
            transition: 0.3s;

            &:hover {
                cursor: pointer;
                font-size: 18px;
            }
        }
    }
</style>