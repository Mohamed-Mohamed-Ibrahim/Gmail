<template>
    <div class="folderLayout" v-show="showFolder">

        <span id="icon3">
            <i class="fa-solid fa-folder fa-2xl" style="color: #183153;"></i>
        </span>

        <span id="icon4" @click="deleteFolder">
            <i class="fa-solid fa-trash" style="color: #183153;"></i>
        </span>

        <p id="name" v-show="!updateFolder"><b>Name: </b>{{ curFolder }}</p>

        <input v-show="updateFolder" id="name" type="text" placeholder="Name" v-model="newName">

        <span id="icon1" @click="updateFolderFn" v-show="updateFolder">
            <i class="fa-solid fa-check" style="color: #183153;"></i>
        </span>

        <span id="icon1" @click="editFolder" v-show="!updateFolder">
            <i class="fa-solid fa-pen-to-square" style="color: #183153;"></i>
        </span>

        <span id="icon2" @click="closeFolder">
            <i class="fa-solid fa-xmark fa-lg" style="color: #183153;"></i>
        </span>

    </div>


</template>

<script>

export default {
    name: 'FolderInfo',
    data() {
        return {
            newName: ""
        }
    },
    props: {
        curFolder: String,
        updateFolder: Boolean,
        mails: Array,
        showFolder: Boolean
    },
    methods: {
        closeFolder() {
            return this.$emit("closeFolder")
        },
        editFolder() {
            this.newName = this.curFolder
            return this.$emit("editFolder")
        },
        updateFolderFn() {
            if( this.newName ){
                let newFolder = this.newName
                this.newName = ""
                return this.$emit('updateFolderFn', newFolder)
            }
        },
        deleteFolder(){
            let newFolder = this.curFolder
            return this.$emit("deleteFolder", newFolder)
        }
    }
}

</script>

<style>

/* .folderLayout {
    height: 50%;
    width: 50%;
    border: 3px solid black;
      border-radius: 30px;
      box-shadow: 10px 10px 10px 10px;    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    border-radius: 2%;
    text-overflow: clip;
} */

.folderLayout {
    top: 15%;
      width: 70%;
      height: 75%;
      left: 300px;
      position: absolute;
      border: 3px solid black;
      border-radius: 30px;
      box-shadow: 10px 10px 10px 10px;
}

#name{
    position: absolute;
    top: 20%;
    left: 20%;
}

.folderLayout #icon1 {
    position: absolute;
    top: 15%;
    right: 5%;
}
.folderLayout #icon3 {
    position: absolute;
    top: 15%;
    left: 8%;
    height: 100%;
    width: auto;
}
.folderLayout #icon4 {
    position: absolute;
    top: 25%;
    right: 5%;
    /* height: 100%;
    width: auto; */
}


.folderLayout #icon2 {
    position: absolute;
    top: 5%;
    right: 5%;
}


</style>