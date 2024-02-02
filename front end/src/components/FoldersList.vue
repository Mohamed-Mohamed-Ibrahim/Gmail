<template>
    <div id="folderInfo">
        <FolderInfo @updateFolderFn="updateFolderFn" @editFolder="editFolder" @closeFolder="closeFolder" @deleteFolder="deleteFolder" :mails="mails" :updateFolder="updateFolder" :showFolder="showFolder" :curFolder="curFolder"></FolderInfo>
    </div>
    
    <div id="folderList"  class="toggle">

        <div id="AddBtn">
           
            <p @click="addFolder">Add Folder  <i class="fa-solid fa-plus" style="color: #183153;"></i></p>
            
        </div>

        <div @click="openFolder(folder)" id="folderItem" v-for="(folder,index) in folders" :key="index">
            <span id="folderIcon">
                <i class="fa-solid fa-folder-open" style="color: #183153;"></i>
            </span>

            {{ folder }}
        </div>
    </div>


</template>

<script>

import FolderInfo from './FolderInfo.vue';

export default {
    name: 'FoldersList',
    data() {
        return {
            updateFolder: false,
            curFolder: "",
            showFolder: false
        }
    },
    props: {
        mails: Array,
        folders: Array
    },
    methods: {
        iconClk() {
            let folderList = document.getElementById("folderList");
            let currentDisplay = folderList.style.display;
            folderList.style.display = currentDisplay === "none" ? "block" : "none";
            let folderInfo = document.getElementById("folderInfo");
            folderInfo.style.display = "none";
        },
        openFolder(folder) {
            this.iconClk();
            this.curFolder = folder;
            let folderInfo = document.getElementById("folderInfo");
            folderInfo.style.display = "block";
            this.showFolder = true
            this.updateFolder = false
        },
        updateFolderFn(newFolder) {
            this.curFolder = newFolder
            this.showFolder = false
            this.updateFolder = false
            return this.$emit("updateFolderFn", newFolder)

        },
        addFolder(){

            this.curFolder = ""

            this.showFolder = true
            this.updateFolder = true

            this.iconClk();
            let folderInfo = document.getElementById("folderInfo");
            folderInfo.style.display = "block";
        },
        closeFolder(){
            this.showFolder = false
            this.updateFolder = false
            let folderInfo = document.getElementById("folderInfo");
            folderInfo.style.display = "none";
        },  
        editFolder() {
            this.showFolder = false
            this.updateFolder = true
            this.showFolder = true

        },
        deleteFolder(newFolder) {
            this.showFolder = false
            this.updateFolder = false
            let folderInfo = document.getElementById("folderInfo");
            folderInfo.style.display = "none";
            return this.$emit('deleteFolder', newFolder);
        },
    },
    components: {
        FolderInfo
    }
}

</script>

<style>

#folderIcon {
    width: 5%;
    height: auto;
    position: absolute;
    left: 8%;
    top: 50%;
    transform: translateY(-50%);
}


#foldersIcon {
    color: wheat;
    position: absolute;
    left: 5%;
    top: 50%;
    transform: translateY(-50%);
    width: 20px;
    height: auto;
}

#folderList {
    height: auto;
    width: 22%;
    position: absolute;
    left: 15%;
    top: 50%;
    transform: translateY(-50%);
    border-radius: 5%;
    display: none;
    border: 3px solid black;
      border-radius: 30px;
      box-shadow: 10px 10px 10px 10px;
}

#AddBtn{
    margin-bottom: 2%;
    margin-top: 2%;
    display: flex;
    justify-content: space-around;
    align-content: space-around;
    cursor: pointer;
}

#folderItem{
    margin-top: 5%;
    margin-bottom: 5%;
    padding: 5%;
    position: relative;
    border-radius: 5%;
    cursor: pointer;
}

#folderItem:hover{
    padding: 5%;
    position: relative;
    background: lightgray;
}


#AddBtn p:hover{
    background: lightgray;
}
#AddBtn p{
    padding: 1%;
    border-radius: 10%;
}
</style>