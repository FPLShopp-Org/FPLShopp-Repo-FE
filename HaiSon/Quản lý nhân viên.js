// Mảng để lưu danh sách nhân viên
let employees = [];

// Hàm thêm nhân viên
function addEmployee() {
    const name = document.getElementById('name').value;
    const position = document.getElementById('position').value;

    if (name && position) {
        const employee = { name, position };
        employees.push(employee);
        displayEmployees();
        clearForm();
    } else {
        alert('Vui lòng nhập đầy đủ thông tin nhân viên.');
    }
}

// Hàm hiển thị danh sách nhân viên
function displayEmployees() {
    const employeeList = document.getElementById('employee-list');
    employeeList.innerHTML = '';

    employees.forEach((employee, index) => {
        const employeeItem = document.createElement('div');
        employeeItem.classList.add('employee-item');
        employeeItem.innerHTML = `
            <span>${employee.name} - ${employee.position}</span>
            <button onclick="deleteEmployee(${index})">Xóa</button>
        `;
        employeeList.appendChild(employeeItem);
    });
}

// Hàm xóa nhân viên
function deleteEmployee(index) {
    employees.splice(index, 1);
    displayEmployees();
}

// Hàm làm sạch form thêm nhân viên
function clearForm() {
    document.getElementById('name').value = '';
    document.getElementById('position').value = '';
}

// Khởi tạo hiển thị ban đầu
displayEmployees();
