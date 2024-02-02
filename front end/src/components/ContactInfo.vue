<template>
    <!-- Div for showing the Contact -->
    <div class="contactLayout" v-show="!updateContact && showContact">
        <span id="icon3">
            <i class="fa-solid fa-user fa-2xl" style="color: #183153;"></i>
        </span>
        <span id="icon4" @click="deleteContact">
            <i class="fa-solid fa-trash" style="color: #183153;"></i>
        </span>
        <p id="name"><b>Name: </b>{{ contactInfo.name }}</p>
        <p id="email"><b style=" display: block;">Emails:</b> {{ getEmails }}</p>
        <span id="icon1" @click="editContact">
            <i class="fa-solid fa-pen-to-square" style="color: #183153;"></i>
        </span>
        <span id="icon2" @click="closeContact">
            <i class="fa-solid fa-xmark fa-lg" style="color: #183153;"></i>
        </span>
    </div>

    <!-- Div for updating the Contact -->
    <div class="contactLayout" v-show="updateContact && showContact">
        <span id="icon3">
            <i class="fa-solid fa-user fa-2xl" style="color: #183153;"></i>
        </span>
        <span id="icon4" @click="deleteContact">
            <i class="fa-solid fa-trash" style="color: #183153;"></i>
        </span>
        <span id="icon2" @click="closeContact">
            <i class="fa-solid fa-xmark fa-lg" style="color: #183153;"></i>
        </span>
        <span id="icon1" @click="updateContactFn">
            <i class="fa-solid fa-check" style="color: #183153;"></i>
        </span>
        <p id="emails"> {{ getEmails }}</p>
        <input id="nameInput" type="text" placeholder="Name" v-model="newName">
        <input id="emailInput" type="email" placeholder="Email" v-model="newEmail">
    </div>
</template>

<script>

export default {
    name: 'ContactInfo',
    data() {
        return {
            newEmail: "",
            newName: ""
        }
    },
    created() {
        this.newName = this.contactInfo.name
    },
    watch: {
        contactInfo() {
            this.newName = this.contactInfo.name
        }
    },
    props: {
        contactInfo: Object,
        updateContact: Boolean,
        showContact: Boolean
    },
    computed: {
        getEmails() {
            const emails = this.contactInfo.email
            if( !emails )
                return "no emails"

            return emails.join(", ");
        }
    },
    methods: {
        closeContact() {
            return this.$emit("closeContact")
        },
        editContact() {
            return this.$emit("editContact")
        },
        updateContactFn() {
            if( this.newEmail && this.newName ){
                let newContact = {
                    name: this.newName,
                    email: this.newEmail
                }
                this.newEmail = ""
                this.newName = ""
                return this.$emit('updateContactFn', newContact)
            }
        },
        deleteContact(){
            let newContact = {
                    name: this.contactInfo.name,
                    email: this.contactInfo.email
            }
            return this.$emit("deleteContact", newContact)
        }
    }
}

</script>


<style>

#emails {
    position: absolute;
    top: 40%;
    left: 20%;
    overflow: hidden;
    text-align: left;
    white-space: pre-line;
    word-wrap: break-word;
    max-width: 50%;
}

/* .contactLayout {
    height: 50%;
    width: 50%;
    background: lightgrey;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    border-radius: 2%;
    text-overflow: clip;
} */

.contactLayout {
    top: 15%;
      width: 70%;
      height: 80%;
      left: 300px;
      position: absolute;
      border: 3px solid black;
      border-radius: 30px;
      box-shadow: 10px 10px 10px 10px;
}


.contactLayout #icon1 {
    position: absolute;
    top: 15%;
    right: 5%;
}
.contactLayout #icon3 {
    position: absolute;
    top: 15%;
    left: 8%;
    height: 100%;
    width: auto;
}
.contactLayout #icon4 {
    position: absolute;
    top: 25%;
    right: 5%;
    /* height: 100%;
    width: auto; */
}


.contactLayout #icon2 {
    position: absolute;
    top: 5%;
    right: 5%;
}


#name{
    position: absolute;
    top: 20%;
    left: 20%;
}

#nameInput{
    position: absolute;
    top: 20%;
    left: 20%;
}

#email{
    position: absolute;
    top: 30%;
    left: 20%;
    /* overflow: hidden; */
    text-align: left;
    white-space: pre-line;
    word-wrap: break-word;
    max-width: 50%;
}


#emailInput{
    position: absolute;
    top: 30%;
    left: 20%;
    /* overflow: hidden; */
    text-align: left;
    white-space: pre-line;
    word-wrap: break-word;
    max-width: 50%;
}

</style>