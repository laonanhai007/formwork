<template>
  <div class="light-card">
      <slot/>
  </div>
</template>

<script setup>
</script>

<style scoped>
.light-card{
    background-color: var(--el-bg-color);
    border-radius: 5px;
    padding: 10px;
}
</style>