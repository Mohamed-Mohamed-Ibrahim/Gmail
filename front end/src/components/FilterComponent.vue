<template>
  <div class="pageFilterMails">
    <nav class="navbar">
      <div class="container-fluid">
        <form class="d-flex" role="search" @submit.prevent="deleteSelectedItems">
          <input v-model="searchInput" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success" type="submit" @click="search">Search</button>
          <div class="dropdown" id="searchBy">
            <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">{{ searchByText }}</button>
            <ul class="dropdown-menu">
              <li @click="updateSearchBy('Date')" class="dropdown-item">Date</li>
              <li @click="updateSearchBy('Sender')" class="dropdown-item">Sender</li>
              <li @click="updateSearchBy('Subject')" class="dropdown-item">Subject</li>
              <li @click="updateSearchBy('Body')" class="dropdown-item">Body</li>
              <li @click="updateSearchBy('Importance')" class="dropdown-item">Importance</li>
            </ul>
          </div>
          <h3>Filter</h3>
          <div class="dropdown" id="sortBy"> 
            <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">{{ sortByText }}</button>
            <ul class="dropdown-menu">
              <li @click="updateSortBy('Date')" class="dropdown-item">Date</li>
              <li @click="updateSortBy('Sender')" class="dropdown-item">Sender</li>
              <li @click="updateSortBy('Subject')" class="dropdown-item">Subject</li>
              <li @click="updateSortBy('Body')" class="dropdown-item">Body</li>
              <li @click="updateSortBy('Importance')" class="dropdown-item">Importance</li>
            </ul>
          </div>
          <button @click="reload" type="button" class="btn btn-outline-success" id="reload">Reload</button>
          <button @click="deleteSelectedItems" class="btn btn-outline-danger" :disabled="!(isDeleteButtonEnabled)" id="delete">Delete</button>
        </form>
      </div>
    </nav>
    <div>
      <ul class="filter-items">
        <li v-for="email in paginatedFilterList" :key="email.id">
          <div class="email-item">
            <div class="email-header clearfix">
              <div class="email-sender">
                <strong>Sender:</strong> {{ email.sender }}
              </div>
              <div class="email-date">
                <strong>Date:</strong> {{ email.date }}
              </div>
            </div>
            <div class="email-subject">
              <strong>Subject:</strong> {{ email.subject }}
            </div>
            <div class="email-body">
              <strong>Body:</strong> {{ email.body }}
            </div>
            <div class="emai-attachment" >
                <select class="fileDropdown" @change="changeCurrentFile">
                  <option value="">None</option>
                  <option :value="attachment" v-for="(attachment,index) in email.attachments" :key="index">{{ index+1 }}</option>
                </select>
                <button class="btn libraryCrlBtn" @click="showFileFn(attachment)" v-if="email.attachments.length != 0">Show File</button>
            </div>
            <div class="email-checkbox-wrapper">
              <input type="checkbox" v-model="email.isSelected" class="email-checkbox">
            </div>
          </div>
        </li>
      </ul>
    </div>
    <div class="pagination">
      <button class = "btn btn-outline-dark" @click="prevPage" :disabled="currentPage === 1">Previous</button>
      <button class = "btn btn-outline-dark" @click="nextPage" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchByText: 'By',
      sortByText: 'Sort by',
      filterList: [],
      searchInput: '',
      itemsPerPage: 3,
      currentPage: 1,
      currentFile: "",
    };
  },
  computed: {
    isDeleteButtonEnabled() {
      return this.filterList.some(email => email.isSelected);
    },

    paginatedFilterList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filterList.slice(start, end);
    },

    totalPages(){
            return Math.ceil(this.filterList.length / this.itemsPerPage);
    },
  },
  methods: {
    changeCurrentFile(event){
          const selectedFile = event.target.value;

          if (selectedFile) {
            this.currentFile = selectedFile

          } 
    },
    showFileFn() {
      let dropdowns = document.querySelectorAll(".fileDropdown")

      if( this.currentFile ) {
        const link = document.createElement('a');
        link.href = this.currentFile;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
        this.currentFile = ""
      }
      
      dropdowns.forEach(item => item.selectedIndex = 0)

    },

    updateSearchBy(value) {
      this.searchByText = value;
    },

    updateSortBy(value) {
      this.sortByText = value;
      if (value === "Date")
        this.filterList.sort((a, b) => new Date(a.date) - new Date(b.date));
      else if (value === "Sender")
        this.filterList.sort((a, b) => a.sender.localeCompare(b.sender));
      else if (value === "Subject")
        this.filterList.sort((a, b) => a.subject.localeCompare(b.subject));
      else if (value === "Body")
        this.filterList.sort((a, b) => a.body.localeCompare(b.body));
      else if (value === "Importance")
        this.filterList.sort((a, b) => String(a.importance).localeCompare(String(b.importance)));
    },

    updateFilterBy(value) {
      this.filterByText = value;
    },

    updateType(value) {
      this.typeText = value;
    },

    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },

    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },

    reload() {
      const MailDTO = {
        sender: this.$root.sender,
        type: "filter",
        receivers: [],
        attachment: [],
      };
      fetch('http://localhost:8080/showMails', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(MailDTO),
      })
        .then(response => response.ok ? response.json() : Promise.reject(`HTTP error! Status: ${response.status}`))
        .then(data => this.filterList = Array.from(data))
        .catch(error => console.error('Error:', error.message));
    },

    async search() {
      const value = this.searchInput;
      const by = this.searchByText.toLocaleLowerCase();
      const MailDTO = {
        sender: this.$root.sender,
        type: 'filter',
        value: value,
        by: by,
        receivers: [],
        attachment: [],
      };
      fetch('http://localhost:8080/search', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(MailDTO),
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json();
        })
        .then(data => {
          this.filterList = Array.from(data);
        })
        .catch(error => console.error('Error:', error.message));
    },

    async deleteSelectedItems() {
      const selectedFilteredItems = this.filterList.filter(email => email.isSelected);
      const selectedItems = [...selectedFilteredItems];
      for (const selectedItem of selectedItems) {
        const MailDTO = {
          id: selectedItem.id,
          date: selectedItem.date,
          sender: selectedItem.sender,
          receivers: selectedItem.receivers,
          importance: selectedItem.importance,
          subject: selectedItem.subject,
          body: selectedItem.body,
          attachment: selectedItem.attachment ? selectedItem.attachment : [],
        };
        try {
          const response = await fetch('http://localhost:8080/deleteFilter', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(MailDTO),
          });
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
        } catch (error) {
          console.error('Error during fetch:', error);
        }
      }
      this.filterList = this.filterList.filter(email => !email.isSelected);
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Grand+Hotel&display=swap');

.libraryCrlBtn {
  border: none;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.fileDropdown{
  border: none;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;

}

.fileDropdown:hover {
  background-color: lightgray;
}


.libraryCrlBtn:hover {
  background-color: lightgray;
}

.pageFilterMails {
  top: 15%;
  display: none;
  width: 70%;
  height: 80%;
  left: 300px;
  position: absolute;
  border: 3px solid black;
  border-radius: 30px;
  box-shadow: 10px 10px 10px 10px;
}
h3 {
  color: rgb(0, 0, 0);
  margin-left: 280px;
  margin-right: 150px;
  font-family: 'Grand Hotel';
}
.navbar {
  border: 1px solid;
  border-radius: 30px 30px 0 0;
  border-bottom: 2px solid rgb(37, 37, 37);
  height: 100px;
}
#searchBy {
  margin-left: 5px;
  margin-right: 10px;
}
#filterBy {
  margin-left: 5px;
}
#sortBy {
  margin-left: 150px;
}
#delete {
  margin-left: 20px;
}
#reload {
  margin-left: 20px;
}
.email-sender {
  float: left;
}
.email-date {
  float: right;
  text-align: right;
}
.clearfix::after {
  content: "";
  clear: both;
  display: block;
}
.filter-items {
  list-style: none;
  padding: 0;
  margin: 0;
}
.email-item {
  border: 1px solid #ddd;
  padding: 15px;
  margin-bottom: 10px;
  width: 95%;
  margin-left: 25px;
}
.email-sender,
.email-date {
  font-weight: bold;
}
.email-subject,
.email-body {
  margin-top: 5px;
}
.email-subject {
  margin-top: 10px;
  font-size: 1.1em;
}
.email-item {
  position: relative;
}
.email-checkbox-wrapper {
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 5px;
}

.pagination {
  position: fixed;
  top: 87% ;
  left: 47% ;
}

.pagination button {
  padding: 8px 12px;
  margin-right: 5px;
  cursor: pointer;
}

.pagination button:disabled {
  cursor: not-allowed;
}
</style>
