<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${param.title != null ? param.title : 'Registraduría Municipal de Nobsa'}</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<style>
    :root {
        --azul-institucional: #004884;
        --azul-acento: #0056b3;
        --azul-hover: #003d6b;
        --blanco: #ffffff;
        --gris-fondo: #f4f6f8;
        --gris-borde: #d1d5db;
        --gris-texto: #4b5563;
        --sombra: 0 1px 3px rgba(0,0,0,0.08);
        --sombra-card: 0 4px 12px rgba(0,0,0,0.1);
        --sombra-modal: 0 20px 60px rgba(0,0,0,0.2);
        --radio: 6px;
    }
    * { margin:0; padding:0; box-sizing:border-box; }
    body {
        font-family: system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
        background-color: var(--gris-fondo);
        color: #1f2937;
        line-height: 1.5;
        -webkit-font-smoothing: antialiased;
    }
    /* Utilidades */
    .container { max-width:1200px; margin:0 auto; padding:0 1.5rem; }
    .btn {
        display:inline-flex; align-items:center; gap:0.5rem;
        padding:0.6rem 1.2rem; border-radius:var(--radio);
        border:none; font-weight:500; font-size:0.95rem;
        cursor:pointer; text-decoration:none;
        transition: all 0.2s ease;
    }
    .btn-primario { background:var(--azul-institucional); color:#fff; }
    .btn-primario:hover { background:var(--azul-hover); transform:translateY(-1px); box-shadow:var(--sombra-card); }
    .btn-secundario { background:#e5e7eb; color:#374151; }
    .btn-secundario:hover { background:#d1d5db; }
    .btn-peligro { background:#dc3545; color:#fff; }
    .btn-peligro:hover { background:#bb2d3b; }
    .btn-exito { background:#198754; color:#fff; }
    .btn-sm { padding:0.4rem 0.8rem; font-size:0.875rem; }
    
    /* Header institucional */
    .header-gov {
        background: var(--azul-institucional);
        color:#fff; padding:1rem 0;
        box-shadow: 0 2px 8px rgba(0,0,0,0.15);
        position:sticky; top:0; z-index:100;
    }
    .header-gov .container { display:flex; justify-content:space-between; align-items:center; }
    .header-gov h1 { font-size:1.25rem; font-weight:600; letter-spacing:-0.3px; }
    .header-gov a { color:#fff; text-decoration:none; font-size:0.9rem; opacity:0.9; }
    .header-gov a:hover { opacity:1; text-decoration:underline; }
    
    /* Layout admin */
    .admin-layout { display:flex; min-height:100vh; }
    .sidebar {
        width:260px; background:var(--azul-institucional);
        color:#fff; position:fixed; height:100vh; overflow-y:auto;
        display:flex; flex-direction:column; padding:1.5rem 0;
    }
    .sidebar-brand { padding:0 1.5rem 1.5rem; border-bottom:1px solid rgba(255,255,255,0.15); }
    .sidebar-brand h2 { font-size:1.1rem; font-weight:600; }
    .sidebar-brand small { opacity:0.75; font-size:0.8rem; display:block; margin-top:4px; }
    .sidebar-menu { list-style:none; padding:1rem 0; flex:1; }
    .sidebar-menu li { margin:2px 0; }
    .sidebar-menu a {
        display:flex; align-items:center; gap:0.75rem;
        padding:0.75rem 1.5rem; color:rgba(255,255,255,0.9);
        text-decoration:none; font-size:0.9rem; border-left:3px solid transparent;
        transition: all 0.15s;
    }
    .sidebar-menu a:hover, .sidebar-menu a.active {
        background:rgba(255,255,255,0.08); color:#fff; border-left-color:#60a5fa;
    }
    .sidebar-footer { padding:1rem 1.5rem; border-top:1px solid rgba(255,255,255,0.15); font-size:0.8rem; opacity:0.7; }
    
    .admin-main { margin-left:260px; flex:1; padding:2rem; }
    
    /* Tablas */
    .card {
        background:#fff; border-radius:8px; box-shadow:var(--sombra);
        overflow:hidden;
    }
    .card-header {
        padding:1.25rem 1.5rem; border-bottom:1px solid #e5e7eb;
        display:flex; justify-content:space-between; align-items:center;
    }
    .card-header h3 { font-size:1.1rem; font-weight:600; color:var(--azul-institucional); }
    .card-body { padding:1.5rem; }
    
    .tabla {
        width:100%; border-collapse:collapse; font-size:0.9rem;
    }
    .tabla thead { background:#f8fafc; }
    .tabla th {
        text-align:left; padding:0.875rem 1.25rem;
        font-weight:600; color:#374151; font-size:0.8rem; text-transform:uppercase; letter-spacing:0.4px;
        border-bottom:2px solid #e5e7eb;
    }
    .tabla td { padding:0.875rem 1.25rem; border-bottom:1px solid #f1f5f9; color:#4b5563; }
    .tabla tbody tr { transition: background 0.15s; }
    .tabla tbody tr:hover { background:#f8fafc; }
    .tabla .acciones { display:flex; gap:0.5rem; }
    
    /* Badges */
    .badge {
        display:inline-block; padding:0.25rem 0.6rem; border-radius:999px;
        font-size:0.75rem; font-weight:600;
    }
    .badge-vigente { background:#d1fae5; color:#065f46; }
    .badge-vencido { background:#fee2e2; color:#991b1b; }
    .badge-cancelado { background:#f3f4f6; color:#4b5563; }
    
    /* Modales */
    .modal-overlay {
        display:none; position:fixed; inset:0; background:rgba(0,0,0,0.45);
        z-index:2000; justify-content:center; align-items:flex-start;
        padding-top:5vh; backdrop-filter: blur(4px);
    }
    .modal-overlay.active { display:flex; }
    .modal {
        background:#fff; border-radius:10px; width:90%; max-width:640px;
        box-shadow:var(--sombra-modal); max-height:85vh; overflow-y:auto;
        animation: modalIn 0.25s ease-out;
    }
    @keyframes modalIn {
        from { opacity:0; transform: translateY(-20px); }
        to { opacity:1; transform: translateY(0); }
    }
    .modal-header {
        padding:1.25rem 1.5rem; border-bottom:1px solid #e5e7eb;
        display:flex; justify-content:space-between; align-items:center; position:sticky; top:0; background:#fff;
    }
    .modal-header h4 { font-size:1.1rem; color:var(--azul-institucional); }
    .modal-close {
        background:none; border:none; font-size:1.25rem; color:#9ca3af; cursor:pointer; padding:0.25rem;
    }
    .modal-close:hover { color:#4b5563; }
    .modal-body { padding:1.5rem; }
    .modal-footer {
        padding:1rem 1.5rem; border-top:1px solid #e5e7eb;
        display:flex; justify-content:flex-end; gap:0.75rem; position:sticky; bottom:0; background:#fff;
    }
    
    /* Formularios */
    .form-group { margin-bottom:1.25rem; }
    .form-group label { display:block; margin-bottom:0.4rem; font-size:0.875rem; font-weight:500; color:#374151; }
    .form-control {
        width:100%; padding:0.6rem 0.875rem; border:1px solid var(--gris-borde);
        border-radius:var(--radio); font-size:0.95rem; transition:border-color 0.15s, box-shadow 0.15s;
    }
    .form-control:focus { outline:none; border-color:var(--azul-acento); box-shadow:0 0 0 3px rgba(0,86,179,0.08); }
    .form-row { display:grid; grid-template-columns:1fr 1fr; gap:1rem; }
    @media (max-width:640px){ .form-row { grid-template-columns:1fr; } }
    
    /* Alertas */
    .alert {
        padding:1rem 1.25rem; border-radius:var(--radio); margin-bottom:1.25rem;
        display:flex; align-items:center; gap:0.75rem; font-size:0.9rem;
    }
    .alert-error { background:#fee2e2; color:#991b1b; border:1px solid #fecaca; }
    .alert-success { background:#d1fae5; color:#065f46; border:1px solid #a7f3d0; }
    
    /* Pagina publica */
    .pagina-publica { background:var(--gris-fondo); min-height:100vh; display:flex; flex-direction:column; }
    .hero-publico {
        background: var(--azul-institucional);
        color:#fff; padding:3rem 0 4rem; text-align:center;
    }
    .hero-publico h2 { font-size:1.75rem; font-weight:600; margin-bottom:0.5rem; }
    .hero-publico p { opacity:0.85; font-size:1.05rem; }
    
    /* Resultado consulta */
    .resultado-mesa {
        background:#fff; border-radius:16px; box-shadow:var(--sombra-modal);
        max-width:680px; margin:-2rem auto 2rem; padding:3rem 2rem; text-align:center; position:relative;
    }
    .etiqueta-inst { font-size:0.875rem; text-transform:uppercase; letter-spacing:1px; color:var(--gris-texto); margin-bottom:0.5rem; }
    .nombre-ciudadano { font-size:1.5rem; font-weight:600; color:#1f2937; margin-bottom:1.5rem; }
    .caja-mesa {
        background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
        border-radius:12px; padding:2rem; margin:1.5rem 0; border:2px solid #e2e8f0;
    }
    .numero-mesa-display {
        font-size:4.5rem; font-weight:800; color:var(--azul-institucional);
        line-height:1; margin:0.5rem 0; letter-spacing:-2px;
    }
    .info-secundaria {
        display:grid; grid-template-columns:1fr 1fr; gap:1.5rem; margin-top:2rem; text-align:left;
    }
    .info-item { padding:1rem; background:#f8fafc; border-radius:8px; }
    .info-item label { display:block; font-size:0.75rem; text-transform:uppercase; color:var(--gris-texto); margin-bottom:0.25rem; }
    .info-item span { font-weight:600; color:#1f2937; font-size:1.05rem; }
    
    .no-inscrito {
        padding:2.5rem; color:#991b1b; background:#fef2f2; border-radius:12px; border:2px solid #fecaca;
    }
    .no-inscrito i { font-size:2.5rem; margin-bottom:1rem; display:block; }
    
    /* Footer */
    .footer-gov {
        background:#1f2937; color:#9ca3af; padding:1.5rem 0; margin-top:auto; font-size:0.85rem; text-align:center;
    }
    
    /* Utilidades */
    .text-center { text-align:center; }
    .mt-1 { margin-top:0.5rem; } .mt-2 { margin-top:1rem; } .mt-3 { margin-top:1.5rem; }
    .mb-1 { margin-bottom:0.5rem; } .mb-2 { margin-bottom:1rem; }
    .d-none { display:none !important; }
</style>