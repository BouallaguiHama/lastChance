import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar">
      <div class="container">
        <div class="nav-brand">
          <h2>📦 Gestion de Stock</h2>
        </div>
        <ul class="nav-menu">
          <li><a routerLink="/dashboard" routerLinkActive="active">Tableau de Bord</a></li>
          <li><a routerLink="/produits" routerLinkActive="active">Produits</a></li>
          <li><a routerLink="/clients" routerLinkActive="active">Clients</a></li>
          <li><a routerLink="/fournisseurs" routerLinkActive="active">Fournisseurs</a></li>
          <li><a routerLink="/achats" routerLinkActive="active">Achats</a></li>
          <li><a routerLink="/ventes" routerLinkActive="active">Ventes</a></li>
        </ul>
      </div>
    </nav>
  `,
  styles: [`
    .navbar {
      background: white;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      position: sticky;
      top: 0;
      z-index: 1000;
    }

    .container {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 24px;
      height: 64px;
    }

    .nav-brand h2 {
      color: #3b82f6;
      margin: 0;
      font-weight: 600;
    }

    .nav-menu {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;
      gap: 32px;
    }

    .nav-menu a {
      color: #6b7280;
      text-decoration: none;
      font-weight: 500;
      padding: 8px 16px;
      border-radius: 8px;
      transition: all 0.3s ease;
    }

    .nav-menu a:hover {
      color: #3b82f6;
      background: #f0f9ff;
    }

    .nav-menu a.active {
      color: #3b82f6;
      background: #dbeafe;
    }

    @media (max-width: 768px) {
      .nav-menu {
        gap: 16px;
        font-size: 14px;
      }

      .nav-menu a {
        padding: 6px 12px;
      }
    }
  `]
})
export class NavigationComponent {}