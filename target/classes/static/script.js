// File: src/main/resources/static/script.js

const API_URL = 'http://localhost:8080/api/users';

// DOM Elements
const userForm = document.getElementById('userForm');
const usersList = document.getElementById('usersList');
const formTitle = document.getElementById('form-title');
const submitBtn = document.getElementById('submitBtn');
const cancelBtn = document.getElementById('cancelBtn');
const userIdInput = document.getElementById('userId');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const ageInput = document.getElementById('age');

let editMode = false;

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadUsers();
    setupEventListeners();
});

function setupEventListeners() {
    userForm.addEventListener('submit', handleSubmit);
    cancelBtn.addEventListener('click', resetForm);
}

// CREATE or UPDATE User
async function handleSubmit(e) {
    e.preventDefault();

    const userData = {
        name: nameInput.value,
        email: emailInput.value,
        age: parseInt(ageInput.value)
    };

    try {
        if (editMode) {
            // UPDATE
            const userId = userIdInput.value;
            await updateUser(userId, userData);
        } else {
            // CREATE
            await createUser(userData);
        }
        resetForm();
        loadUsers();
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

// CREATE - POST request
async function createUser(userData) {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });

    if (!response.ok) {
        throw new Error('Failed to create user');
    }

    return await response.json();
}

// READ - GET all users
async function loadUsers() {
    try {
        const response = await fetch(API_URL);
        const users = await response.json();
        displayUsers(users);
    } catch (error) {
        console.error('Error loading users:', error);
        usersList.innerHTML = '<p class="empty-state">Error loading users</p>';
    }
}

// UPDATE - PUT request
async function updateUser(id, userData) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    });

    if (!response.ok) {
        throw new Error('Failed to update user');
    }

    return await response.json();
}

// DELETE - DELETE request
async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadUsers();
        } else {
            throw new Error('Failed to delete user');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

// Display users in the UI
function displayUsers(users) {
    if (users.length === 0) {
        usersList.innerHTML = '<p class="empty-state">No users found. Add your first user!</p>';
        return;
    }

    usersList.innerHTML = users.map(user => `
        <div class="user-card">
            <div class="user-info">
                <h3>${user.name}</h3>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong>Age:</strong> ${user.age}</p>
                <p><strong>ID:</strong> ${user.id}</p>
            </div>
            <div class="user-actions">
                <button class="btn btn-edit" onclick="editUser(${user.id})">Edit</button>
                <button class="btn btn-delete" onclick="deleteUser(${user.id})">Delete</button>
            </div>
        </div>
    `).join('');
}

// Edit user - populate form
async function editUser(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        const user = await response.json();

        userIdInput.value = user.id;
        nameInput.value = user.name;
        emailInput.value = user.email;
        ageInput.value = user.age;

        editMode = true;
        formTitle.textContent = 'Update User';
        submitBtn.textContent = 'Update User';
        cancelBtn.style.display = 'inline-block';

        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    } catch (error) {
        alert('Error loading user: ' + error.message);
    }
}

// Reset form to initial state
function resetForm() {
    userForm.reset();
    userIdInput.value = '';
    editMode = false;
    formTitle.textContent = 'Add New User';
    submitBtn.textContent = 'Add User';
    cancelBtn.style.display = 'none';
}