# Script para agregar meta tag de Mixed Content a todos los HTML

$metaTag = '  <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">'

$htmlFiles = Get-ChildItem -Path "front" -Filter "*.html" -Recurse

$count = 0
foreach ($file in $htmlFiles) {
    $content = Get-Content $file.FullName -Raw -Encoding UTF8
    
    # Verificar si ya tiene el meta tag
    if ($content -notmatch 'Content-Security-Policy') {
        # Buscar head e insertar despues
        $newContent = $content -replace '(<head[^>]*>)', "`$1`r`n$metaTag"
        Set-Content -Path $file.FullName -Value $newContent -Encoding UTF8 -NoNewline
        Write-Host "Agregado a: $($file.Name)" -ForegroundColor Green
        $count++
    } else {
        Write-Host "Ya tiene CSP: $($file.Name)" -ForegroundColor Yellow
    }
}

Write-Host "`nTotal procesados: $count archivos" -ForegroundColor Cyan
