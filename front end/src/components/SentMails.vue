<template>
  <div class="pageSentMails">
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

          <input v-model="filterInput" class="form-control me-2" type="search" placeholder="Filter" aria-label="Search">
          <button class="btn btn-outline-warning" type="submit" @click="filter">Filter</button>

          <div class="dropdown" id="filterBy">
            <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">{{ filterByText }}</button>
            <ul class="dropdown-menu">
              <li @click="updateFilterBy('Date')" class="dropdown-item">Date</li>
              <li @click="updateFilterBy('Sender')" class="dropdown-item">Sender</li>
              <li @click="updateFilterBy('Subject')" class="dropdown-item">Subject</li>
              <li @click="updateFilterBy('Body')" class="dropdown-item">Body</li>
              <li @click="updateFilterBy('Importance')" class="dropdown-item">Importance</li>
            </ul>
          </div>

          <h3>Sent</h3>

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
      <ul class="sent-items">
        <li v-for="email in paginatedSentList" :key="email.id" >
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
                <button class="libraryCrlBtn" @click="showFileFn(attachment)" v-if="email.attachments.length != 0">Show File</button>

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
      filterByText: 'By',
      typeText: 'sent',
      sentList: [],
      searchInput: '',
      filterInput: '',
      itemsPerPage: 3,
      currentPage: 1,
      files: [],
      currentFile: ""
    };
  },
  watch: {
    sentList() {
      console.log(this.sentList)
    }
  },
  computed: {
    isDeleteButtonEnabled() {
      return this.sentList.some(email => email.isSelected);
    },

    paginatedSentList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.sentList.slice(start, end);
    },

    totalPages(){
            return Math.ceil(this.sentList.length / this.itemsPerPage);
    },
  },

  methods: {
    changeCurrentFile(event){
          const selectedFile = event.target.value;

          if (selectedFile) {
            this.currentFile = selectedFile
            // console.log(selectedFile)
          } 
    },
    showFileFn() {
      let dropdowns = document.querySelectorAll(".fileDropdown")

      if( this.currentFile ) {
        const link = document.createElement('a');
        link.href = this.currentFile;
        // console.log(this.currentFile)
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
        this.sentList.sort((a, b) => new Date(a.date) - new Date(b.date));
      else if (value === "Sender")
        this.sentList.sort((a, b) => a.sender.localeCompare(b.sender));
      else if (value === "Subject")
        this.sentList.sort((a, b) => a.subject.localeCompare(b.subject));
      else if (value === "Body")
        this.sentList.sort((a, b) => a.body.localeCompare(b.body));
      else if (value === "Importance")
        this.sentList.sort((a, b) => String(a.importance).localeCompare(String(b.importance)));
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
        type: "sent",
        receivers: [],
        attachment: [],
      };
      fetch('http://localhost:8080/showMails', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(MailDTO),
      })
        .then(response => response.ok ? response.json() : Promise.reject(`HTTP error! Status: ${response.status}`))
        .then(data => {
        this.sentList = Array.from(data);
      })
        .catch(error => console.error('Error:', error.message))

    },

    async deleteSelectedItems() {
      const selectedSentItems = this.sentList.filter(email => email.isSelected);
      const selectedItems = [...selectedSentItems];

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
          const response = await fetch('http://localhost:8080/deleteSent', {
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

      this.sentList = this.sentList.filter(email => !email.isSelected);
    },

    async search() {
      const value = this.searchInput;
      const by = this.searchByText.toLocaleLowerCase();

      const MailDTO = {
        sender: this.$root.sender,
        type: "sent",
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
          this.sentList = Array.from(data);
        })
        .catch(error => console.error('Error:', error.message));
    },

    async filter() {
      const value = this.filterInput;
      const by = this.filterByText.toLocaleLowerCase();

      const MailDTO = {
        sender: this.$root.sender,
        type: 'sent',
        value: value,
        by: by,
        receivers: [],
        attachment: [],
      };

      fetch('http://localhost:8080/filter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(MailDTO),
      })

    },
  },
};
</script>

<style scoped>
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

.pageSentMails {
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
  margin-left: 110px;
  margin-right: 30px;
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
  margin-left: 100px;
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

.sent-items {
  list-style: none;
  padding: 0;
  margin: 0;
  position: relative;
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
