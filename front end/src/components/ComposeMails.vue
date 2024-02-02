<template>
  <div class="pageComposeMails">
    <h1>Compose</h1>
    <form class="was-validated" @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="validationServer01" class="form-label">To</label>
        <input type="text" class="form-control is-valid" id="validationServer01" required>
      </div>
      <div class="mb-3">
        <label for="validationServer02" class="form-label">Subject</label>
        <input type="text" class="form-control is-valid" id="validationServer02" required>
      </div>
      <div class="mb-3" id="priority">
        <label for="validationCustom04" class="priorty-label">Priority</label>
        <select class="form-select" id="validationCustom04" required>
          <option selected disabled value="">Choose...</option>
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>
      <div class="mb-3">
        <label for="validationTextarea" class="form-label">Message</label>
        <textarea class="form-control" id="validationTextarea" placeholder="Write your message here..." required></textarea>
      </div>
      <div class="mb-3 attachment-container">
        <label for="formFileMultiple" class="attachment-label">Attach Files (optional)</label>
        <input class="form-control" type="file" id="formFileMultiple" multiple>
        <small class="text-muted">You can upload multiple files.</small>
      </div>
      <div class="mb-3">
        <button type="submit" class="btn btn-outline-success">Send Mail</button>
        <button class="btn btn-outline-primary" @click.prevent="draft">Draft</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: 0,
    };
  },
  methods: {
    onSubmit() {
      const to = document.getElementById('validationServer01').value;
      const subject = document.getElementById('validationServer02').value;
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

      const MailDTO = {
        type: 'sent',
        id: ++this.id,
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
      });
    },

    draft() {
      const to = document.getElementById('validationServer01').value;
      const subject = document.getElementById('validationServer02').value;
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

      const MailDTO = {
        type: 'draft',
        id: ++this.id,
        sender: this.$root.sender,
        receivers: receivers,
        importance: priority,
        subject: subject,
        body: message,
        attachment: attachment,
      };

      fetch('http://localhost:8080/draft', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(MailDTO),
      });
    },
  },
};
</script>

<style scoped>
.pageComposeMails {
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

h1 {
  color: rgb(0, 0, 0);
  font-family: 'Grand Hotel';
  border: 2px solid;
  border-bottom: 4px solid;
  border-radius: 20px 20px 0 0;
  padding: 30px;
}

form {
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
</style>