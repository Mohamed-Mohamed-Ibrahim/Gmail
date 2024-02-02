<template>
  <header>
    <img id = "menu" @click = "toggleSidebar" width="40" height="40" src="https://img.icons8.com/external-others-amoghdesign/24/external-list-multimedia-solid-24px-others-amoghdesign.png"/>
    <h1>Gmail</h1>
  </header>
</template>

<script>
export default {
  data() {
    return {
      sidebarOpen: false
    };
  },
  methods: {
    toggleSidebar() {
      const list = document.querySelector(".list");
      const menu = document.querySelector("#menu");

      if (this.sidebarOpen) {
        list.classList.remove("show");
        menu.classList.remove("show");
      } else {
        list.classList.add("show");
        menu.classList.add("show");
      }

      this.sidebarOpen = !this.sidebarOpen;

      // Reset the transition delay
      document.querySelectorAll(".list-item").forEach((item, index) => {

        const delay = this.sidebarOpen ? 0.5 * (index + 1) : 0;
        item.style.transitionDelay = `${delay}s`;

        // Exclude hover and activate events from the delay
        item.addEventListener('mouseover', () => {
          item.style.transitionDelay = '0s';
        });

        item.addEventListener('mouseout', () => {
          item.style.transitionDelay = '0s';
        });

        item.addEventListener('click', () => {
          item.style.transitionDelay = '0s';
        });
      });
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Grand+Hotel&display=swap');

  header {
    background-image: linear-gradient(90deg, rgb(185, 185, 185), rgba(104, 104, 104, 0.733), rgba(237, 237, 237, 0.651)) ;
    top: 0;
    width: 100%;
    height: 10%;
    display: flex;
    border: 4px solid black;
    justify-content: center;
    align-items: center;
    position: fixed;
    color: black;
    font-family: 'Grand Hotel';
    padding: 20px;
  }

  #menu{
    left : 30px ;
    position: fixed;
    cursor: pointer ;
  }

</style>
