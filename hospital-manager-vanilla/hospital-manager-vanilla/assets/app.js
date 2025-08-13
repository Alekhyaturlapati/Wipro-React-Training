// Hospital Manager - Vanilla JS + Bootstrap + localStorage
(function(){
  const LS_KEY = 'hospital_manager_data_v1';

  /*** State & Storage ***/
  const defaultData = {
    patients: [],
    doctors: [],
    departments: [
      { id: genId(), name: 'General Medicine', head: '—' },
      { id: genId(), name: 'Cardiology', head: '—' },
      { id: genId(), name: 'Orthopedics', head: '—' }
    ],
    appointments: []
  };

  function load() {
    try{
      return JSON.parse(localStorage.getItem(LS_KEY)) || defaultData;
    }catch(e){ return defaultData; }
  }

  function save() {
    localStorage.setItem(LS_KEY, JSON.stringify(db));
    refresh();
  }

  function genId() { return Math.random().toString(36).slice(2,10); }

  let db = load();

  /*** Routing (simple view toggler) ***/
  const views = {
    dashboard: document.querySelector('#view-dashboard'),
    patients: document.querySelector('#view-patients'),
    doctors: document.querySelector('#view-doctors'),
    departments: document.querySelector('#view-departments'),
    appointments: document.querySelector('#view-appointments')
  };
  document.querySelectorAll('[data-route]').forEach(a=>{
    a.addEventListener('click', (e)=>{
      e.preventDefault();
      const route = a.getAttribute('data-route');
      showView(route);
      document.querySelectorAll('nav .nav-link').forEach(n=>n.classList.remove('active','text-white'));
      document.querySelectorAll('nav .nav-link').forEach(n=>n.classList.add('text-white-50'));
      a.classList.add('active','text-white');
      a.classList.remove('text-white-50');
    });
  });
  function showView(route){
    Object.values(views).forEach(v=>v.classList.add('d-none'));
    views[route].classList.remove('d-none');
  }

  /*** DOM helpers ***/
  const el = (html)=>{
    const t = document.createElement('template');
    t.innerHTML = html.trim();
    return t.content.firstElementChild;
  };

  /*** Dashboard ***/
  function updateCounts(){
    document.querySelector('#countPatients').textContent = db.patients.length;
    document.querySelector('#countDoctors').textContent = db.doctors.length;
    document.querySelector('#countDepartments').textContent = db.departments.length;
    const today = new Date().toISOString().slice(0,10);
    const todayAppts = db.appointments.filter(a=>a.date===today).length;
    document.querySelector('#countAppointmentsToday').textContent = todayAppts;
  }

  /*** Patients ***/
  const tbodyPatients = document.querySelector('#tbodyPatients');
  const searchPatients = document.querySelector('#searchPatients');
  function renderPatients(){
    const q = (searchPatients.value||'').toLowerCase();
    tbodyPatients.innerHTML = '';
    db.patients
      .filter(p => [p.id,p.name,p.phone].join(' ').toLowerCase().includes(q))
      .forEach(p=>{
        const tr = el(`<tr>
          <td>${p.id}</td>
          <td>${p.name}</td>
          <td>${p.gender||''}</td>
          <td>${p.age||''}</td>
          <td>${p.phone||''}</td>
          <td class="text-nowrap">
            <button class="btn btn-sm btn-outline-primary me-1"><i class="bi bi-pencil"></i></button>
            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
          </td>
        </tr>`);
        tr.querySelector('.btn-outline-primary').addEventListener('click', ()=>openPatientModal(p));
        tr.querySelector('.btn-outline-danger').addEventListener('click', ()=>{
          if(confirm('Delete this patient?')){
            db.patients = db.patients.filter(x=>x.id!==p.id);
            save();
          }
        });
        tbodyPatients.appendChild(tr);
      });
  }
  searchPatients.addEventListener('input', renderPatients);

  function openPatientModal(p){
    const modal = new bootstrap.Modal('#modalPatient');
    document.querySelector('#patientId').value = p?.id || '';
    document.querySelector('#patientName').value = p?.name || '';
    document.querySelector('#patientGender').value = p?.gender || 'Male';
    document.querySelector('#patientAge').value = p?.age || '';
    document.querySelector('#patientPhone').value = p?.phone || '';
    document.querySelector('#patientAddress').value = p?.address || '';
    modal.show();
  }
  document.querySelector('#formPatient').addEventListener('submit', (e)=>{
    e.preventDefault();
    const id = document.querySelector('#patientId').value || genId();
    const data = {
      id,
      name: document.querySelector('#patientName').value.trim(),
      gender: document.querySelector('#patientGender').value,
      age: +document.querySelector('#patientAge').value,
      phone: document.querySelector('#patientPhone').value.trim(),
      address: document.querySelector('#patientAddress').value.trim()
    };
    const i = db.patients.findIndex(p=>p.id===id);
    if(i>=0) db.patients[i] = data; else db.patients.push(data);
    save();
    bootstrap.Modal.getInstance(document.querySelector('#modalPatient')).hide();
    e.target.reset();
  });

  /*** Departments ***/
  const tbodyDepartments = document.querySelector('#tbodyDepartments');
  function renderDepartments(){
    tbodyDepartments.innerHTML = '';
    db.departments.forEach(d=>{
      const tr = el(`<tr>
        <td>${d.id}</td>
        <td>${d.name}</td>
        <td>${d.head||''}</td>
        <td class="text-nowrap">
          <button class="btn btn-sm btn-outline-primary me-1"><i class="bi bi-pencil"></i></button>
          <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
        </td>
      </tr>`);
      tr.querySelector('.btn-outline-primary').addEventListener('click', ()=>openDeptModal(d));
      tr.querySelector('.btn-outline-danger').addEventListener('click', ()=>{
        if(confirm('Delete this department?')){
          // also detach from doctors
          db.doctors = db.doctors.map(doc => doc.deptId===d.id ? {...doc, deptId:null} : doc);
          db.departments = db.departments.filter(x=>x.id!==d.id);
          save();
        }
      });
      tbodyDepartments.appendChild(tr);
    });
    populateDeptSelects();
  }
  function openDeptModal(d){
    const modal = new bootstrap.Modal('#modalDepartment');
    document.querySelector('#departmentId').value = d?.id || '';
    document.querySelector('#departmentName').value = d?.name || '';
    document.querySelector('#departmentHead').value = d?.head || '';
    modal.show();
  }
  document.querySelector('#formDepartment').addEventListener('submit', (e)=>{
    e.preventDefault();
    const id = document.querySelector('#departmentId').value || genId();
    const d = {
      id,
      name: document.querySelector('#departmentName').value.trim(),
      head: document.querySelector('#departmentHead').value.trim()
    };
    const i = db.departments.findIndex(x=>x.id===id);
    if(i>=0) db.departments[i] = d; else db.departments.push(d);
    save();
    bootstrap.Modal.getInstance(document.querySelector('#modalDepartment')).hide();
    e.target.reset();
  });

  /*** Doctors ***/
  const tbodyDoctors = document.querySelector('#tbodyDoctors');
  const searchDoctors = document.querySelector('#searchDoctors');
  function renderDoctors(){
    const q = (searchDoctors.value||'').toLowerCase();
    tbodyDoctors.innerHTML = '';
    db.doctors
      .filter(d => [d.id,d.name,d.specialty, deptName(d.deptId), d.phone].join(' ').toLowerCase().includes(q))
      .forEach(d=>{
        const tr = el(`<tr>
          <td>${d.id}</td>
          <td>${d.name}</td>
          <td>${deptName(d.deptId)||''}</td>
          <td>${d.specialty||''}</td>
          <td>${d.phone||''}</td>
          <td class="text-nowrap">
            <button class="btn btn-sm btn-outline-primary me-1"><i class="bi bi-pencil"></i></button>
            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
          </td>
        </tr>`);
        tr.querySelector('.btn-outline-primary').addEventListener('click', ()=>openDoctorModal(d));
        tr.querySelector('.btn-outline-danger').addEventListener('click', ()=>{
          if(confirm('Delete this doctor?')){
            db.appointments = db.appointments.filter(a=>a.doctorId!==d.id);
            db.doctors = db.doctors.filter(x=>x.id!==d.id);
            save();
          }
        });
        tbodyDoctors.appendChild(tr);
      });
  }
  searchDoctors.addEventListener('input', renderDoctors);

  function populateDeptSelects(){
    const options = db.departments.map(d=>`<option value="${d.id}">${d.name}</option>`).join('');
    document.querySelector('#doctorDept').innerHTML = options;
    document.querySelector('#apptDept').innerHTML = `<option value="">All</option>` + options;
  }
  function openDoctorModal(d){
    const modal = new bootstrap.Modal('#modalDoctor');
    populateDeptSelects();
    document.querySelector('#doctorId').value = d?.id || '';
    document.querySelector('#doctorName').value = d?.name || '';
    document.querySelector('#doctorPhone').value = d?.phone || '';
    document.querySelector('#doctorSpecialty').value = d?.specialty || '';
    document.querySelector('#doctorDept').value = d?.deptId || (db.departments[0]?.id || '');
    modal.show();
  }
  document.querySelector('#formDoctor').addEventListener('submit', (e)=>{
    e.preventDefault();
    const id = document.querySelector('#doctorId').value || genId();
    const d = {
      id,
      name: document.querySelector('#doctorName').value.trim(),
      phone: document.querySelector('#doctorPhone').value.trim(),
      specialty: document.querySelector('#doctorSpecialty').value.trim(),
      deptId: document.querySelector('#doctorDept').value
    };
    const i = db.doctors.findIndex(x=>x.id===id);
    if(i>=0) db.doctors[i] = d; else db.doctors.push(d);
    save();
    bootstrap.Modal.getInstance(document.querySelector('#modalDoctor')).hide();
    e.target.reset();
  });

  function deptName(id){
    return db.departments.find(d=>d.id===id)?.name || '';
  }
  function doctorName(id){
    return db.doctors.find(d=>d.id===id)?.name || '';
  }
  function patientName(id){
    return db.patients.find(p=>p.id===id)?.name || '';
  }

  /*** Appointments ***/
  const tbodyAppointments = document.querySelector('#tbodyAppointments');
  const searchAppointments = document.querySelector('#searchAppointments');
  function renderAppointments(){
    const q = (searchAppointments.value||'').toLowerCase();
    tbodyAppointments.innerHTML = '';
    db.appointments
      .filter(a => [a.id, a.date, a.time, patientName(a.patientId), doctorName(a.doctorId), deptName(a.deptId), a.notes].join(' ').toLowerCase().includes(q))
      .sort((a,b) => (a.date+a.time).localeCompare(b.date+b.time))
      .forEach(a=>{
        const tr = el(`<tr>
          <td>${a.id}</td>
          <td>${a.date}</td>
          <td>${a.time}</td>
          <td>${patientName(a.patientId)}</td>
          <td>${doctorName(a.doctorId)}</td>
          <td>${deptName(a.deptId)}</td>
          <td class="text-truncate" style="max-width:220px">${a.notes||''}</td>
          <td class="text-nowrap">
            <button class="btn btn-sm btn-outline-primary me-1"><i class="bi bi-pencil"></i></button>
            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
          </td>
        </tr>`);
        tr.querySelector('.btn-outline-primary').addEventListener('click', ()=>openAppointmentModal(a));
        tr.querySelector('.btn-outline-danger').addEventListener('click', ()=>{
          if(confirm('Delete this appointment?')){
            db.appointments = db.appointments.filter(x=>x.id!==a.id);
            save();
          }
        });
        tbodyAppointments.appendChild(tr);
      });
  }
  searchAppointments.addEventListener('input', renderAppointments);

  function populatePeopleSelects(){
    document.querySelector('#apptPatient').innerHTML =
      db.patients.map(p=>`<option value="${p.id}">${p.name}</option>`).join('');
    const deptSel = document.querySelector('#apptDept');
    const docSel = document.querySelector('#apptDoctor');
    // filter doctors by selected dept
    const refreshDocs = ()=>{
      const dept = deptSel.value;
      const docs = db.doctors.filter(d => !dept || d.deptId===dept);
      docSel.innerHTML = docs.map(d=>`<option value="${d.id}">${d.name}</option>`).join('');
    };
    deptSel.onchange = refreshDocs;
    refreshDocs();
  }

  function openAppointmentModal(a){
    const modal = new bootstrap.Modal('#modalAppointment');
    populateDeptSelects();
    populatePeopleSelects();
    document.querySelector('#appointmentId').value = a?.id || '';
    document.querySelector('#apptDate').value = a?.date || new Date().toISOString().slice(0,10);
    document.querySelector('#apptTime').value = a?.time || '10:00';
    document.querySelector('#apptDept').value = a?.deptId || (db.departments[0]?.id || '');
    // trigger doc refresh
    document.querySelector('#apptDept').dispatchEvent(new Event('change'));
    document.querySelector('#apptDoctor').value = a?.doctorId || (db.doctors[0]?.id || '');
    document.querySelector('#apptPatient').value = a?.patientId || (db.patients[0]?.id || '');
    document.querySelector('#apptNotes').value = a?.notes || '';
    modal.show();
  }
  document.querySelector('#formAppointment').addEventListener('submit', (e)=>{
    e.preventDefault();
    const id = document.querySelector('#appointmentId').value || genId();
    const appt = {
      id,
      date: document.querySelector('#apptDate').value,
      time: document.querySelector('#apptTime').value,
      deptId: document.querySelector('#apptDept').value,
      doctorId: document.querySelector('#apptDoctor').value,
      patientId: document.querySelector('#apptPatient').value,
      notes: document.querySelector('#apptNotes').value.trim()
    };
    const i = db.appointments.findIndex(x=>x.id===id);
    if(i>=0) db.appointments[i] = appt; else db.appointments.push(appt);
    save();
    bootstrap.Modal.getInstance(document.querySelector('#modalAppointment')).hide();
    e.target.reset();
  });

  /*** Import / Export ***/
  document.querySelector('#btnExport').addEventListener('click', ()=>{
    const blob = new Blob([JSON.stringify(db, null, 2)], {type:'application/json'});
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'hospital-data.json';
    a.click();
    URL.revokeObjectURL(url);
  });
  document.querySelector('#importFile').addEventListener('change', (e)=>{
    const file = e.target.files[0];
    if(!file) return;
    const reader = new FileReader();
    reader.onload = ()=>{
      try{
        const parsed = JSON.parse(reader.result);
        if(!parsed || typeof parsed!=='object') throw 'Invalid file';
        db = Object.assign({}, defaultData, parsed);
        save();
        alert('Import successful!');
      }catch(err){ alert('Import failed: ' + err); }
    };
    reader.readAsText(file);
    e.target.value = '';
  });

  /*** Initial render ***/
  function refresh(){
    updateCounts();
    renderPatients();
    renderDepartments();
    renderDoctors();
    renderAppointments();
  }
  refresh();
  // Start on dashboard
  showView('dashboard');
  window.openAppointmentModal = openAppointmentModal;
})();