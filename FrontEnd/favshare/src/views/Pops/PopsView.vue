<template>
  <!-- eslint-disable -->
  <div class="no-scroll">
    <nav-bar></nav-bar>
    <div class="pops-list">
      <pops-list></pops-list>
    </div>
    <div class="bottom">
      <bottom-navigation-bar class="bottom"></bottom-navigation-bar>
    </div>
  </div>
</template>

<script>
import PopsList from "@/components/Pops/PopsList.vue";
import BottomNavigationBar from "@/components/BottomNavigationBar.vue";
import NavBar from "@/components/NavBar.vue";

export default {
  name: "PopsView",
  components: {
    PopsList,
    BottomNavigationBar,
    NavBar,
  },
};
</script>

<style scoped>
.pops-body {
  padding-top: 130px;
}
.top {
  position: fixed;
  width: 100%;
  top: 0;
  z-index: 2;
}
.pops-view {
  padding-top: 5px;
}
.bottom {
  position: fixed;
  width: 100%;
  bottom: 0;
}
.no-scroll::-webkit-scrollbar {
  display: none;
}
</style>
