<template>

  <div id="contactListToggle">
      <div id="contactInfo">
          <ContactInfo @deleteContact="deleteContact" @editContact="editContact" @closeContact="closeContact" @updateContactFn="updateContactFn"  :contactInfo="curContact" :updateContact="updateContact" :showContact="showContact"></ContactInfo>
      </div>



      <div id="contactList"  class="toggle">
          <div>
              <input type="text" v-model="search" id="searchBtn" @keyup.enter="searchContact"/>
              
          </div>
          <div id="sortAddBtn">
              <p @click="sortList">Sort <i class="fa-solid fa-arrow-down-a-z" style="color: #183153;" v-show="sortAsc"></i>
                  <i class="fa-solid fa-arrow-down-z-a" style="color: #183153;" v-show="!sortAsc"></i>
              </p>
          
              <p @click="addContact">Add Contact <i class="fa-solid fa-plus" style="color: #183153;"></i></p>
              
          </div>
          <div id="addBtn">
              
          </div>
          <div @click="openContact(contact)" id="contactItem" v-for="(contact,name) in curContacts" :key="name">
              <span id="contactIcon">
                  <i class="fa-solid fa-user" style="color: #26395a;"></i>
              </span>

              {{ contact.name }}
          </div>
      </div>
  </div>
</template>

<script>

import ContactInfo from './ContactInfo.vue';

export default {
  name: 'ContactsList',
  data() {
      return {
          curContact: { name: "", email: [] },
          search: '',
          sortAsc: true,
          showContact: false,
          updateContact: false,
          curContacts: []
      };
  },
  props: {
      contacts: Array
  },
  compements: {
      ContactInfo,
  },
  created() {
      this.curContacts = this.contacts
  },
  watch: {
      contacts() {
          this.curContacts = this.contacts
      }
  },
  methods: {
      iconClk() {
          let contactList = document.getElementById("contactList");
          let currentDisplay = contactList.style.display;
          contactList.style.display = currentDisplay === "none" ? "block" : "none";
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "none";
      },
      openContact(contact) {
          this.iconClk();
          this.curContact = contact;
          this.showContact = true
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "block" ;
          this.updateContact = false
      },
      updateContactFn(newContact) {
          
          this.showContact = false
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "none";
          this.updateContact = false
          // this.curContact.name = newContact.name
          // this.curContact.email = [ ...this.curContact.email, newContact.email]
          newContact.email = [ ...this.curContact.email, newContact.email]
          return this.$emit("updateContactFn", newContact)

      },
      searchContact(){

          if(this.search !== ''){
              const newContact = {
                  name: this.search,
                  email: [this.search]
              }
              this.search = '';
              
              this.curContacts = this.contacts.filter(item => item.name.indexOf(newContact.name) !== -1);


          }

      },
      addContact(){

          this.curContact = { name: "", email: [] }
          
          this.showContact = true

          this.updateContact = true

          this.iconClk();
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "block";
      },
      deleteContact(newContact) {
          this.showContact = false
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "none";
          this.updateContact = false
          return this.$emit('deleteContact', newContact);
      },
      sortList() {
          return this.$emit('sortList');
      },
      closeContact(){
          this.showContact = false
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "none";
          this.updateContact = false
      },  
      editContact() {
          this.showContact = false
          let contactInfo = document.getElementById("contactInfo");
          contactInfo.style.display = "none";
          this.updateContact = true
          this.showContact = true
          contactInfo.style.display = "block";
      },
  },
  components: { ContactInfo }
}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Grand+Hotel&display=swap');

p{
  display: inline;
}

#contactsIcon {
  color: wheat;
  position: absolute;
  right: 5%;
  top: 50%;
  transform: translateY(-50%);
  width: 20px;
  height: auto;
}

#searchBtn{
  margin: 2% 0;
  border: 0px solid;
  text-overflow: clip;
}

#searchBtn:focus{
  outline: none;
  border: 0px solid;
  

}

#contactList{

  height: auto;
  width: 22%;
  position: absolute;
  left: 15%;
  top: 50%;
  border: 3px solid black;
  transform: translateY(-50%);
  border-radius: 30px;
  box-shadow: 10px 10px 10px 10px;    
  display: none;

}

#contactItem{
  margin-top: 5%;
  margin-bottom: 5%;
  padding: 5%;
  position: relative;
  border-radius: 5%;
  
}

#contactItem:hover{
  padding: 5%;
  position: relative;
  background: lightgray;
}

#sortAddBtn{
  margin-bottom: 2%;
  margin-top: 2%;
  display: flex;
  justify-content: space-around;
  align-content: space-around;
  
}

#sortAddBtn p:hover{
  background: lightgray;
}
#sortAddBtn p{
  padding: 1%;
  border-radius: 10%;
}

#contactIcon {
  width: 5%;
  height: auto;
  position: absolute;
  left: 8%;
  top: 50%;
  transform: translateY(-50%);
}

.contact {
  width: 80%;
  height: 80%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateY(-50%);
  transform: translateX(-50%);
}


</style>