import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { Produit } from '../../models/produit.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <h1 class="mb-4">Tableau de Bord</h1>

      <div class="stats-grid mb-4">
        <div class="stat-card">
          <div class="stat-icon">📦</div>
          <div class="stat-content">
            <h3>{{ totalProduits }}</h3>
            <p>Produits Total</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon">⚠️</div>
          <div class="stat-content">
            <h3>{{ produitsStockFaible.length }}</h3>
            <p>Stock Faible</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon">💰</div>
          <div class="stat-content">
            <h3>{{ valeurStock | currency:'EUR':'symbol':'1.0-0' }}</h3>
            <p>Valeur Stock</p>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-icon">🚫</div>
          <div class="stat-content">
            <h3>{{ produitsRupture }}</h3>
            <p>En Rupture</p>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6">
          <div class="card">
            <h3>Produits avec Stock Faible</h3>
            <div *ngIf="produitsStockFaible.length === 0" class="text-center mt-3">
              <p>Aucun produit avec stock faible 👍</p>
            </div>
            <div *ngFor="let produit of produitsStockFaible" class="stock-item">
              <div class="stock-info">
                <strong>{{ produit.nom }}</strong>
                <span class="badge badge-warning">{{ produit.quantiteStock }} / {{ produit.seuilMinimum }}</span>
              </div>
              <div class="stock-bar">
                <div class="stock-progress" 
                     [style.width.%]="(produit.quantiteStock / produit.seuilMinimum) * 100">
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-6">
          <div class="card">
            <h3>Actions Rapides</h3>
            <div class="quick-actions">
              <a routerLink="/produits/nouveau" class="btn btn-primary">
                <span class="material-icons">add</span>
                Nouveau Produit
              </a>
              <a routerLink="/achats/nouveau" class="btn btn-success">
                <span class="material-icons">shopping_cart</span>
                Nouvel Achat
              </a>
              <a routerLink="/ventes/nouveau" class="btn btn-outline">
                <span class="material-icons">sell</span>
                Nouvelle Vente
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 20px;
    }

    .stat-card {
      background: white;
      border-radius: 12px;
      padding: 24px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
      display: flex;
      align-items: center;
      gap: 16px;
      transition: transform 0.3s ease;
    }

    .stat-card:hover {
      transform: translateY(-4px);
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
    }

    .stat-icon {
      font-size: 40px;
      width: 64px;
      height: 64px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f0f9ff;
      border-radius: 12px;
    }

    .stat-content h3 {
      margin: 0;
      font-size: 28px;
      font-weight: 700;
      color: #1f2937;
    }

    .stat-content p {
      margin: 4px 0 0 0;
      color: #6b7280;
      font-weight: 500;
    }

    .row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 24px;
    }

    .col-md-6 {
      min-width: 0;
    }

    .stock-item {
      padding: 16px 0;
      border-bottom: 1px solid #e5e7eb;
    }

    .stock-item:last-child {
      border-bottom: none;
    }

    .stock-info {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 8px;
    }

    .stock-bar {
      height: 8px;
      background: #f3f4f6;
      border-radius: 4px;
      overflow: hidden;
    }

    .stock-progress {
      height: 100%;
      background: linear-gradient(90deg, #f59e0b, #d97706);
      transition: width 0.3s ease;
    }

    .quick-actions {
      display: flex;
      flex-direction: column;
      gap: 12px;
    }

    .quick-actions .btn {
      justify-content: flex-start;
    }

    @media (max-width: 768px) {
      .stats-grid {
        grid-template-columns: 1fr;
      }

      .row {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class DashboardComponent implements OnInit {
  totalProduits = 0;
  produitsStockFaible: Produit[] = [];
  valeurStock = 0;
  produitsRupture = 0;

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.loadDashboardData();
  }

  loadDashboardData() {
    this.apiService.getProduits().subscribe({
      next: (produits) => {
        this.totalProduits = produits.length;
        this.valeurStock = produits.reduce((total, produit) => 
          total + (produit.quantiteStock * produit.prixUnitaire), 0);
        this.produitsRupture = produits.filter(p => p.quantiteStock === 0).length;
      },
      error: (error) => console.error('Erreur lors du chargement des produits:', error)
    });

    this.apiService.getProduitsStockFaible().subscribe({
      next: (produits) => {
        this.produitsStockFaible = produits;
      },
      error: (error) => console.error('Erreur lors du chargement des produits en stock faible:', error)
    });
  }
}