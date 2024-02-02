<template>
  <div>
    <div class="list">
      <button @click="toggleReceivedMails" class="list-item btn btn-outline-dark">
        <img width="30" height="30" src="https://img.icons8.com/dotty/80/000000/mailbox-plane.png"/>
        &nbsp;&nbsp;Inbox
      </button>
      <button @click = "toggleTrashMails" class="list-item btn btn-outline-dark">
        <img width="30" height="30" src="https://img.icons8.com/comic/100/000000/experimental-trash-comic.png"/>
        &nbsp;&nbsp;Trash
      </button>
      <button @click="toggleComposeMails" class="list-item btn btn-outline-dark">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width="30" height="30" src="https://img.icons8.com/ios/50/composing-mail.png"/>
        &nbsp;&nbsp;Compose
      </button>
      <button @click=" toggleSentMails" class="list-item btn btn-outline-dark">
        <img width="30" height="30" src="https://img.icons8.com/quill/50/000000/sent.png"/>
        &nbsp;&nbsp;Sent
      </button>
      <button @click="toggleDraftMails" class="list-item btn btn-outline-dark">
        <img width="30" height="30" src="https://img.icons8.com/carbon-copy/100/000000/edit-file.png"/>
        &nbsp;&nbsp;Draft
      </button>
      <button @click="toggleFilterMails" class="list-item btn btn-outline-dark">
        <img width="30" height="30" src="https://img.icons8.com/pastel-glyph/64/000000/filtered-file.png"/>
        &nbsp;&nbsp;Filter
      </button>
      <button @click="toggleContactList" class="list-item btn btn-outline-dark">
        &nbsp;&nbsp;&nbsp;&nbsp;<img width="30" height="30" src="https://img.icons8.com/comic/100/000000/experimental-user-comic.png"/>
        &nbsp;&nbsp;Contact
      </button>
      <button @click="toggleFolderList" class="list-item btn btn-outline-dark">
        &nbsp;&nbsp;&nbsp;&nbsp;<img width="30" height="30" src="https://img.icons8.com/?size=256&id=71186&format=png"/>
        &nbsp;&nbsp;Folder
      </button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    toggleReceivedMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element2.style.display === "block")
        element2.style.display = "none" ;

     else if (element3.style.display === "block")
        element3.style.display = "none" ;
    
      else if (element4.style.display === "block")
        element4.style.display = "none" ;

      else if (element5.style.display === "block")
        element5.style.display = "none" ;

      else if (element6.style.display === "block")
        element6.style.display = "none" ;
      
      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;
      
      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;
      

      element1.style.display = element1.style.display === "block" ? "none" : "block";
    },
    toggleComposeMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none" ;

     else if (element3.style.display === "block")
        element3.style.display = "none" ;
    
      else if (element4.style.display === "block")
        element4.style.display = "none" ;

      else if (element5.style.display === "block")
        element5.style.display = "none" ;

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;

      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;

      element2.style.display = element2.style.display === "block" ? "none" : "block";
    },
    toggleDraftMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none" ;

     else if (element2.style.display === "block")
        element2.style.display = "none" ;
    
      else if (element4.style.display === "block")
        element4.style.display = "none" ;

      else if (element5.style.display === "block")
        element5.style.display = "none" ;

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;

      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;


      element3.style.display = element3.style.display === "block" ? "none" : "block";
    },
    toggleSentMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none" ;

      else if (element2.style.display === "block")
        element2.style.display = "none" ;
    
      else if (element3.style.display === "block")
        element3.style.display = "none" ;

      else if (element5.style.display === "block")
        element5.style.display = "none" ;

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;

      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;


      element4.style.display = element4.style.display === "block" ? "none" : "block";
    },
    toggleTrashMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none" ;

      else if (element2.style.display === "block")
        element2.style.display = "none" ;
    
      else if (element3.style.display === "block")
        element3.style.display = "none" ;

      else if (element4.style.display === "block")
        element4.style.display = "none" ;

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;
      
        else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;


        element5.style.display = element5.style.display === "block" ? "none" : "block";
    },

    toggleFilterMails() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none";

      else if (element2.style.display === "block")
        element2.style.display = "none";

      else if (element3.style.display === "block")
        element3.style.display = "none";

      else if (element4.style.display === "block")
        element4.style.display = "none";

      else if (element5.style.display === "block")
        element5.style.display = "none";

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;
      
      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;

      element6.style.display = element6.style.display === "block" ? "none" : "block";
    },
    toggleContactList() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none";

      else if (element2.style.display === "block")
        element2.style.display = "none";

      else if (element3.style.display === "block")
        element3.style.display = "none";

      else if (element4.style.display === "block")
        element4.style.display = "none";

      else if (element5.style.display === "block")
        element5.style.display = "none";

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;

      else if (element9.style.display === "block")
        element9.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;

      element7.style.display = element7.style.display === "block" ? "none" : "block";
    },
    toggleFolderList() {
      const element1 = document.querySelector('.pageReceivedMails');
      const element2 = document.querySelector('.pageComposeMails');
      const element3 = document.querySelector('.pageDraftMails');
      const element4 = document.querySelector('.pageSentMails');
      const element5 = document.querySelector('.pageTrashMails');
      const element6 = document.querySelector('.pageFilterMails');
      const element7 = document.getElementById('contactList');
      const element8 = document.getElementById("contactInfo");
      const element9 = document.getElementById("folderList");
      const element10 = document.getElementById("folderInfo");

      if (element1.style.display === "block")
        element1.style.display = "none";

      else if (element2.style.display === "block")
        element2.style.display = "none";

      else if (element3.style.display === "block")
        element3.style.display = "none";

      else if (element4.style.display === "block")
        element4.style.display = "none";

      else if (element5.style.display === "block")
        element5.style.display = "none";

      else if (element6.style.display === "block")
        element6.style.display = "none" ;

      else if (element8.style.display === "block")
        element8.style.display = "none" ;

      else if (element7.style.display === "block")
        element7.style.display = "none" ;

      else if (element10.style.display === "block")
        element10.style.display = "none" ;

      element9.style.display = element9.style.display === "block" ? "none" : "block";
    },
  },
};
</script>

<style scoped>
.list {
  border: black solid 5px;
  border-top: 2px solid;
  width: 250px;
  height: 90vh;
  background-image: linear-gradient(90deg, rgb(200, 200, 200), rgba(104, 104, 104, 0.733), rgba(237, 237, 237, 0.651));
  position: fixed;
  bottom: 0;
  left: -250px;
  transition: left 0.4s ease-in-out;
}

.list.show {
  left: 0px;
}

.list-item {
  background-image: linear-gradient(90deg, rgb(200, 200, 200), rgba(133, 133, 133, 0.733), rgba(237, 237, 237, 0.651));
  width: 100%;
  text-align: center;
  border-radius: 0;
  border: 2px white;
  padding: 20px;
  font-size: 18px;
  opacity: 0;
  transform: translateX(-250px);
  transition: opacity 0.4s ease-in-out, transform 0.4s, background-color 0.4s, color 0.4s ease-in-out;
}

.list-item.btn:active {
  background-color: rgb(96, 96, 96);
  border: 2px solid white;
  color: rgb(221, 219, 219);
  transition: background-color 2ms, color 2ms;
}

.list-item:hover {
  transition: background-color 0.2s, color 0.2s;
  transition-delay: 0s;
  border-bottom: 2px solid white;
}

.btn:hover img {
  filter: invert(1);
}

.btn img {
  filter: invert(0);
}

.list.show .list-item {
  opacity: 1;
  transform: translateX(0);
}
</style>
