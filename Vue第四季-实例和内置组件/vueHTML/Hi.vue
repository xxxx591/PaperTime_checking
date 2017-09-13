<template>
    <div>
        <h2>{{ msg }}</h2>
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name:'hi',
    data(){
        return{
            msg:'我就是喜欢乐乐！怎么样吗？'
        }
    }
}
</script>

<style>

</style>
