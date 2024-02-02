<template>
  <div v-if = 'sender'>
    <SideBar></SideBar>
    <HeaderPage></HeaderPage>
    <ReceivedMails></ReceivedMails>
    <ComposeMails></ComposeMails>
    <DraftMails></DraftMails>
    <SentMails></SentMails>
    <TrashMails></TrashMails>
    <FilterComponent></FilterComponent>
    <ContactsList  @deleteContact="deleteContact" @updateContactFn="updateContactFn" @sortList="sortList" :contacts="contacts"></ContactsList>
    <FoldersList @deleteFolder="deleteFolder" @updateFolderFn="updateFolderFn" :folders="folders"/>
  </div>

  <div v-if = '!sender'>
    <SignUp></SignUp>
    <SignIn></SignIn>
  </div>  
</template>

<script>
import SideBar from "./components/SideBar.vue";
import HeaderPage from "./components/HeaderPage.vue" ;
import ReceivedMails from "./components/ReceivedMails.vue";
import ComposeMails from "./components/ComposeMails.vue";
import DraftMails from "./components/DraftMails.vue";
import SentMails from "./components/SentMails.vue";
import TrashMails from "./components/TrashMails.vue";
import FilterComponent from "./components/FilterComponent.vue";
import SignUp from "./components/SignUp.vue";
import SignIn from './components/SignIn.vue';
import ContactsList from "./components/ContactsList.vue";
import FoldersList from "./components/FoldersList.vue";

export default {
  name: 'App',
  components: {
    SideBar,
    ReceivedMails,
    HeaderPage,
    ComposeMails,
    DraftMails,
    SentMails,
    TrashMails,
    FilterComponent,
    SignUp,
    SignIn,
    ContactsList,
    FoldersList,
},
data() {
  return {
    contacts: [],
    folders: [],
    sender: this.$root.sender,
  }
},
async created() {
  this.contacts = await this.getContacts()
  this.folders = [
    "parent",
    "safety"
  ],
  this.mails = [
    "mail 1",
    "mail 2",
  ]
},
methods: {
  async getContacts() {
    
    const reuslt = await fetch(`http://localhost:8080/contacts/${this.$root.sender  }`)

    return await reuslt.json()

  },
  async getFolders() {
    
    const reuslt = await fetch('http://localhost:8080/folders/')

    return await reuslt.json()

  },
  async sortList() {

    this.contacts = this.contacts.sort((b, a) => b.name.localeCompare(a.name));

  },
  async updateContactFn(curContact) {
    
    let newContact = {
      name: "",
      email: []
    }

    const index = this.contacts.findIndex(item => item.name === curContact.name);
    console.log(index)
    if (index !== -1) {
      
      newContact = this.contacts[index]
      
      newContact["user"] = this.$root.sender

      this.contacts[index] = curContact

      curContact["user"] = this.$root.sender

      await fetch('http://localhost:8080/contact/edit', {
        method: 'post',
        headers: {
              'Content-type': "application/json; charset=UTF-8"
            },
        body: JSON.stringify({
          newContact,
          curContact
        }),
      })

    } else {

      this.contacts = [ ...this.contacts,  curContact]

      curContact["user"] = this.$root.sender

      newContact["user"] = this.$root.sender

      await fetch('http://localhost:8080/contact/edit', {
        method: 'post',
        headers: {
              'Content-type': "application/json; charset=UTF-8"
            },
        body: JSON.stringify({
          newContact,
          curContact
        }),
      })

    }

  },
  async deleteContact(newContact) {

    let contact = {
      name: newContact["name"],
      email: newContact["email"],
      user: this.$root.sender
    }

    await fetch('http://localhost:8080/contact/delete', {
      method: 'delete',
      headers: {
            'Content-type': "application/json; charset=UTF-8"
          },
      body: JSON.stringify(contact),
    })
    console.log(newContact)
    this.contacts = this.contacts.filter(item => item.name !== newContact.name);

  },
  async updateFolderFn(newFolder) {
    // PUT can be used with POST
    await fetch('http://localhost:8080/folder/add', {
      method: 'post',
      headers: {
            'Content-type': "application/json; charset=UTF-8"
          },
      body: JSON.stringify(newFolder),
    })
    console.log(newFolder)
    this.folders = [ ...this.folders,  newFolder]
  },
  async deleteFolder(newFolder) {
    await fetch('http://localhost:8080/folder/delete', {
      method: 'delete',
      headers: {
            'Content-type': "application/json; charset=UTF-8"
          },
      body: JSON.stringify(newFolder),
    })

    this.folders = this.folders.filter(item => item != newFolder)
  }
}
}
</script>

<style>
#app {
font-family: Avenir, Helvetica, Arial, sans-serif;
-webkit-font-smoothing: antialiased;
-moz-osx-font-smoothing: grayscale;
text-align: center;
color: #2c3e50;
margin-top: 60px;
}
</style>
