<template>
  <div class="pageDraftMails">
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

          <h3>Draft</h3>

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

    <form v-if="selectedEmail" class="was-validated" id = 'draft' @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="validationServer01" class="form-label">To</label>
        <input v-model="selectedEmail.sender" type="text" class="form-control is-valid" id="validationServer01" required>
      </div>
      <div class="mb-3">
        <label for="validationServer03" class="form-label">Subject</label>
        <input v-model="selectedEmail.subject" type="text" class="form-control is-valid" id="validationServer03" required>
      </div>

      <div class="mb-3" id="priority">
        <label for="validationCustom04" class="priorty-label">Priority</label>
        <select v-model="selectedEmail.importance" class="form-select" id="validationCustom04" required>
          <option selected disabled value="">Choose...</option>
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>
      
      <div class="mb-3">
        <label for="validationTextarea" class="form-label">Body</label>
        <textarea v-model="selectedEmail.body" class="form-control" id="validationTextarea" placeholder="Write your message here..." required></textarea>
      </div>
      <div class="mb-3 attachment-container">
        <label for="formFileMultiple" class="attachment-label">Attach Files (optional)</label>
        <input class="form-control" type="file" id="formFileMultiple" multiple>
        <small class="text-muted">You can upload multiple files.</small>
      </div>
      <div class="mb-3">
        <button type="submit" class="btn btn-outline-success">Send Mail</button>
        <button class="btn btn-outline-warning" @click.prevent="edit">Edit</button>
      </div>
    </form>

    <div v-if= "!selectedEmail">
      <ul class="draft-items">
        <li v-for="(email) in paginatedDraftList" :key="email.id" @click="selectEmail(email, $event)">
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
      filterInput: '',
      typeText: 'Default',
      searchInput: '',
      draftList: [],
      selectedEmail: null,
      itemsPerPage: 3,
      currentPage: 1,
    };
  },

  computed: {
    isDeleteButtonEnabled() {
      return this.draftList.some(email => email.isSelected);
    },

    totalPages() {
      return Math.ceil(this.draftList.length / this.itemsPerPage);
    },

    paginatedDraftList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.draftList.slice(start, end);
    },

  },

  methods: {
    updateSearchBy(value) {
      this.searchByText = value;
    },

    updateSortBy(value) {
      this.sortByText = value;
      if (value === "Date")
        this.draftList.sort((a, b) => new Date(a.date) - new Date(b.date));
      else if (value === "Sender")
        this.draftList.sort((a, b) => a.sender.localeCompare(b.sender));
      else if (value === "Subject")
        this.draftList.sort((a, b) => a.subject.localeCompare(b.subject));
      else if (value === "Body")
        this.draftList.sort((a, b) => a.body.localeCompare(b.body));
      else if (value === "Importance")
        this.draftList.sort((a, b) => String(a.importance).localeCompare(String(b.importance)));
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

    async search() {
      const value = this.searchInput;
      const by = this.searchByText.toLocaleLowerCase();

      const MailDTO = {
        sender: this.$root.sender,
        type: "draft",
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
          this.draftList = Array.from(data);
        })
        .catch(error => console.error('Error:', error.message));
      },

      async filter() {
        const value = this.filterInput;
        const by = this.filterByText.toLocaleLowerCase();

        const MailDTO = {
          sender: this.$root.sender,
          type: 'draft',
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
    
      reload() {
        this.selectedEmail = null ;
        const MailDTO = {
          sender: this.$root.sender,
          type: "draft",
          receivers: [],
          attachment: [],
        };

        fetch('http://localhost:8080/showMails', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(MailDTO),
        })
          .then(response => response.ok ? response.json() : Promise.reject(`HTTP error! Status: ${response.status}`))
          .then(data => this.draftList = Array.from(data))
          .catch(error => console.error('Error:', error.message));
      },

      async deleteSelectedItems() {
        const selectedDraftItems = this.draftList.filter(email => email.isSelected);
        const selectedItems = [...selectedDraftItems];

        for (const selectedItem of selectedItems) {

          const MailDTO = {
            id: selectedItem.id,
            date: selectedItem.date,
            sender: selectedItem.sender,
            receivers: selectedItem.receivers,
            importance: selectedItem.importance,
            subject: selectedItem.subject,
            body: selectedItem.body,
            attachment: selectedItems.attachment ? selectedItems.attachment : []
          };

          try {
            const response = await fetch('http://localhost:8080/deleteDraft', {
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

        this.draftList = this.draftList.filter(email => !email.isSelected);

      },

      selectEmail(email, event) {
        const isCheckbox = event.target.classList.contains('email-checkbox');
        
        if (!isCheckbox) {
          this.selectedEmail = { ...email };
        }
        event.stopPropagation();
      },


      onSubmit() {
        const to = this.selectedEmail.sender;
        const subject = this.selectedEmail.subject;
        const priority = this.selectedEmail.importance;
        const message = this.selectedEmail.body;
        const files = this.selectedEmail.files;

        const attachment = [];

        if (files != null ){
          for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const filePath = URL.createObjectURL(file);
            attachment.push(filePath);
          }
        }
          
        const receivers = to.split(' ');

        const MailDTO = {
          type: "sent",
          id : ++this.id,  
          sender: this.$root.sender,
          receivers: receivers,
          importance: priority,
          subject: subject,
          body: message,
          attachment: attachment ? attachment : [],
        };

        fetch('http://localhost:8080/send', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(MailDTO),
        })
      },
        
      async edit() {
        try {
          const id = this.selectedEmail.id;

          const deleteMailDTO = {
            id: id,
            date: this.selectedEmail.date,
            sender: this.selectedEmail.sender,
            receivers: this.selectedEmail.receivers,
            importance: this.selectedEmail.importance,
            subject: this.selectedEmail.subject,
            body: this.selectedEmail.body,
            attachment:this.selectedEmail.attachment ? this.selectedEmail.attachment : [],
          };

          const deleteResponse = await fetch('http://localhost:8080/deleteDraft', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(deleteMailDTO),
          });

          if (!deleteResponse.ok) {
            throw new Error('Network response was not ok');
          }

          const to = document.getElementById('validationServer01').value;
          const subject = document.getElementById('validationServer03').value;
          const priority = document.getElementById('validationCustom04').value;
          const message = document.getElementById('validationTextarea').value;
          const files = document.getElementById('formFileMultiple').files;
          const attachment = [];

          for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const filePath = URL.createObjectURL(file);
            attachment.push(filePath);
          }
          const receivers = to.split(' ');

          const newDraft = {
            type: "draft",
            id: id,
            sender: this.$root.sender,
            receivers: receivers,
            importance: priority,
            subject: subject,
            body: message,
            attachment: attachment ? attachment : []
          };

          const createDraftResponse = await fetch('http://localhost:8080/draft', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newDraft),
          });

          if (!createDraftResponse.ok) {
            throw new Error('Network response was not ok');
          }

          this.draftList = this.draftList.filter(email => email.id !== id);
        
        }
        catch (error) {
          console.error('Error during edit:', error);
        }
    },

  },
};
</script>
    
<style scoped>
  
  .pageDraftMails {
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
  
  h3{
    color: rgb(0, 0, 0);
    margin-left: 110px;
    margin-right: 30px;
    font-family: 'Grand Hotel';
  }
  
  .navbar {
    border: 1px solid;
    border-radius: 30px 30px 0 0 ;
    border-bottom: 2px solid rgb(37, 37, 37);
    height: 100px;
  }
  
  #searchBy {
      margin-left: 5px;
      margin-right: 10px;
  }

  #filterBy{
    margin-left: 5px;
  }
  
  #sortBy {
      margin-left: 100px;
  }
  
  #delete {
      margin-left: 20px;
  }

  #reload{
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

  .draft-items {
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

  #draft {
    display: flex;
    margin-left: 25%;
    margin-top: 20px;
    width: 50%;
    height: 75%;
    flex-direction: column;
    align-items: center;
    padding: 30px;
    border-radius: 20px;
    border: 2px solid black;
    box-shadow: 5px 5px 5px rgb(0, 0, 0);
  }

  .form-label {
    text-align: left;
  }

  .form-label {
    display: block;
    margin-bottom: 5px;
    margin-left: 30px;
  }

  .form-control {
    padding-left: 15px;
  }

  .form-control {
    margin-left: 30px;
  }

  .form-label {
    font-size: 16px;
    color: rgb(74, 74, 93);
  }

  #formFileMultiple {
    margin-left: 80px;
  }

  .btn {
    margin: 2px;
  }

  .attachment-container {
    width: 60%;
  }

  .attachment-label {
    margin-left: -50px;
  }

  #priority {
    text-align: left;
    margin-left: -50px;
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
    