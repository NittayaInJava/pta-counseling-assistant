const statusEl = document.getElementById("status");

const insuranceNumberEl = document.getElementById("insuranceNumber");
const pznEl = document.getElementById("pzn");
const dosageTextEl = document.getElementById("dosageText");

const btnGenerate = document.getElementById("btnGenerate");
const btnDemo2 = document.getElementById("btnDemo2");

const resultEmpty = document.getElementById("resultEmpty");
const result = document.getElementById("result");

const patientName = document.getElementById("patientName");
const patientAge = document.getElementById("patientAge");
const patientInsurance = document.getElementById("patientInsurance");

const currentMedsBody = document.getElementById("currentMedsBody");
const alertsEl = document.getElementById("alerts");

const lastDispense = document.getElementById("lastDispense");
const prevMeds = document.getElementById("prevMeds");
const checklistEl = document.getElementById("checklist");

function setStatus(msg) {
  statusEl.textContent = msg || "";
}

function severityClass(sev) {
  const s = (sev || "").toLowerCase();
  if (s === "high") return "high";
  if (s === "medium") return "medium";
  return "low";
}

function renderPlan(plan) {
  resultEmpty.classList.add("hidden");
  result.classList.remove("hidden");

  patientName.textContent = plan.patient?.fullName ?? "-";
  patientAge.textContent = (plan.patient?.age ?? "-").toString();
  patientInsurance.textContent = plan.patient?.healthInsurance ?? "-";

  // Current meds table
  currentMedsBody.innerHTML = "";
  (plan.currentMedications || []).forEach(m => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td><strong>${m.name ?? "-"}</strong><div class="muted">${m.standardAdvice ?? ""}</div></td>
      <td>${m.activeIngredient ?? "-"}</td>
      <td>${m.pzn ?? "-"}</td>
      <td>${m.dosageText ?? "-"}</td>
    `;
    currentMedsBody.appendChild(tr);
  });

  // Alerts
  alertsEl.innerHTML = "";
  const alerts = plan.alerts || [];
  if (alerts.length === 0) {
    const div = document.createElement("div");
    div.className = "alert";
    div.textContent = "Keine Alerts für diese Demo-Konfiguration.";
    alertsEl.appendChild(div);
  } else {
    alerts.forEach(a => {
      const div = document.createElement("div");
      div.className = "alert";
      div.innerHTML = `
        <span class="badge ${severityClass(a.severity)}">${(a.severity ?? "LOW").toUpperCase()}</span>
        <strong>${a.type ?? "GENERAL"}</strong>
        <div style="margin-top:6px">${a.message ?? ""}</div>
      `;
      alertsEl.appendChild(div);
    });
  }

  // History
  lastDispense.textContent = plan.historySummary?.lastDispenseAt ?? "-";
  const prev = plan.historySummary?.previousMedications || [];
  prevMeds.textContent = prev.length ? prev.join(", ") : "-";

  // Checklist
  checklistEl.innerHTML = "";
  (plan.checklist || []).forEach(item => {
    const li = document.createElement("li");
    li.textContent = item;
    checklistEl.appendChild(li);
  });
}

async function generatePlan(payload) {
  setStatus("Sende Anfrage…");

  try {
    const res = await fetch("/api/counseling/erezept", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    const text = await res.text();
    if (!res.ok) {
      setStatus(`Fehler (${res.status}): ${text}`);
      return;
    }

    const plan = JSON.parse(text);
    renderPlan(plan);
    setStatus("Beratungsplan erfolgreich erzeugt.");
  } catch (err) {
    console.error(err);
    setStatus("Netzwerk-/Serverfehler. Läuft das Backend auf http://localhost:8080 ?");
  }
}

btnGenerate.addEventListener("click", () => {
  const payload = {
    patientInsuranceNumber: insuranceNumberEl.value.trim(),
    medications: [
      {
        pzn: pznEl.value.trim(),
        dosageText: dosageTextEl.value.trim()
      }
    ]
  };
  generatePlan(payload);
});

btnDemo2.addEventListener("click", () => {
  // Demo: Anna Beispiel + Pantoprazol
  insuranceNumberEl.value = "B987654321";
  pznEl.value = "33333333";
  dosageTextEl.value = "1-0-0";
  setStatus("Demo-Daten gesetzt. Klicke jetzt auf „Beratungsplan erzeugen“.");
});
