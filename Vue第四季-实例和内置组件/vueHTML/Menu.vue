<template>
    <ul>
        <li v-for='(val,index) in list'>
            {{val}}
        </li>
    </ul>
</template>
<script>
export default {
    data() {
        return {
            list: ['apple', 'banana', 'orange']
        }
    },
}
</script>
