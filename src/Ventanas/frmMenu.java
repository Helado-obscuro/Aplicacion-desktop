package Ventanas;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

//Importar clases**
import Modelo.Empleado;
import Modelo.Empresa;
import Modelo.Cliente;
import Modelo.Proveedor;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Venta;
import Modelo.DetalleVenta;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Controlador.productosControlador;

import java.awt.ComponentOrientation;
import Modelo.Producto;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class frmMenu extends javax.swing.JFrame implements ActionListener {
    //Instancia del jDialog

    //Modelo de las tablas 
    DefaultTableModel modeloEmpleado = new DefaultTableModel();
    DefaultTableModel modeloCliente = new DefaultTableModel();
    DefaultTableModel modeloProveedor = new DefaultTableModel();
    DefaultTableModel modeloPedido = new DefaultTableModel();
    DefaultTableModel modeloProductoVenta = new DefaultTableModel();
    DefaultTableModel modeloDetalleVenta = new DefaultTableModel();
    //Modelo Combox
    DefaultComboBoxModel comboClienteVenta = new DefaultComboBoxModel();
    //modelo para la fecha 
    Date date = new Date();
    //Instancias de las clases 
    Empleado objEmpleado = new Empleado();
    Empresa objEmpresa = new Empresa();
    Cliente objCliente = new Cliente();
    Proveedor objProveedor = new Proveedor();
    Producto objProducto = new Producto();
    Pedido objPedido = new Pedido();
    Producto objProductoVenta = new Producto();
    productosControlador pcontrol = new productosControlador(this);
    Venta objVenta = new Venta();
    DetalleVenta objDetalleVenta = new DetalleVenta();
    //Instancia Ventanas
    frmAgregarProducto agregarPartida;
    //Variables del JpopupMenu
    private final JPopupMenu popupMenu;
    private final JPopupMenu popupMenuPoductoVenta;
    private final JMenuItem menuItemAgregarProductoVenta;
    private final JMenuItem menuItemAdd;
    private final JMenuItem menuItemRemove;
    private final JMenuItem menuItemRecibido;
    private final JMenuItem menuItemTransferido;
    private final JMenuItem menuItemEnviado;
    private final JMenuItem menuItemCompletado;
    private final JMenuItem menuItemDesglosarPedido;

    frmLogin inicio;
    
    //variables auxiliares 
    int banderitaDetalleVentecita=0;

    //Variables para mover la pantaña
    int x = 0, y = 0;

//instancia para el filtro de busqueda 
    private TableRowSorter trsfiltro;
    
    //Leer idEmpresa
    BufferedReader in ;
    public frmMenu(frmLogin l) {

        inicio = l;
        initComponents();
           actualizarListaProductos();
           
           checarempresa();
 

//        init();

        // constructs the popup menu
        popupMenu = new JPopupMenu();
        popupMenuPoductoVenta = new JPopupMenu();
        menuItemAgregarProductoVenta = new JMenuItem("Agregar producto");
        menuItemAdd = new JMenuItem("Agrgera nueva fila");
        menuItemRemove = new JMenuItem("Remover la fila actual");
        menuItemTransferido = new JMenuItem("Cambiar estatus a transferido");
        menuItemRecibido = new JMenuItem("Cambiar estatus a recibido");
        menuItemEnviado = new JMenuItem("Cambiar estatus a enviado");
        menuItemCompletado = new JMenuItem("Cambiar estatus a completado");
        menuItemDesglosarPedido = new JMenuItem("Detalle de pedido");

        menuItemAdd.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemRecibido.addActionListener(this);
        menuItemTransferido.addActionListener(this);
        menuItemEnviado.addActionListener(this);
        menuItemCompletado.addActionListener(this);
        menuItemDesglosarPedido.addActionListener(this);
        menuItemAgregarProductoVenta.addActionListener(this);

        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemTransferido);
        popupMenu.add(menuItemRecibido);
        popupMenu.add(menuItemEnviado);
        popupMenu.add(menuItemCompletado);
        popupMenu.add(menuItemDesglosarPedido);
        popupMenuPoductoVenta.add(menuItemAgregarProductoVenta);

        // sets the popup menu for the table
        jtbPedido.setComponentPopupMenu(popupMenu);
        jtbPedido.addMouseListener(new TableMouseListener(jtbPedido));

        jtbProductoVenta.setComponentPopupMenu(popupMenuPoductoVenta);
        jtbProductoVenta.addMouseListener(new TableMouseListener(jtbProductoVenta));

    }

    public class TableMouseListener extends MouseAdapter {

        private JTable table;

        public TableMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mousePressed(MouseEvent event) {
            // selects the row at which point the mouse is clicked
            Point point = event.getPoint();
            int currentRow = table.rowAtPoint(point);
            table.setRowSelectionInterval(currentRow, currentRow);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuItemAdd) {
            addNewRow();
        } else if (menu == menuItemRemove) {
            removeCurrentRow();
        } else if (menu == menuItemTransferido) {
            transferido();

        } else if (menu == menuItemRecibido) {
            recibido();
        } else if (menu == menuItemEnviado) {
            enviado();
        } else if (menu == menuItemCompletado) {
            completado();
        } else if (menu == menuItemDesglosarPedido) {
            detalle();
        } else if (menu == menuItemAgregarProductoVenta) {
            abrirVentana();
        }

    }

    private void addNewRow() {
        modeloPedido.addRow(new String[0]);
    }

    private void removeCurrentRow() {
        int selectedRow = jtbPedido.getSelectedRow();
        modeloPedido.removeRow(selectedRow);
    }
    
    public Producto getProducto(){
        return objProducto;
    }

    private void removeAllRows() {
        int rowCount = modeloPedido.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            modeloPedido.removeRow(0);
        }
    }
    
    private void checarempresa() {
        try {
            in = new BufferedReader(new FileReader("UserLogged.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(frmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.out.println("ID Empresa: " + line);
                if (line.equals("1")) {
                    System.out.println("Empresa 1 en uso ");
                } else {
                     System.out.println("Empresa 2 en uso ");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(frmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(frmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Fin 
    private void recibido() {
        int selectedRow = jtbPedido.getSelectedRow();
        modeloPedido.setValueAt("Recibido", selectedRow, 3);
        objPedido.modificarEstatus("Recibido", ((Integer) modeloPedido.getValueAt(selectedRow, 0)).intValue());
    }

    private void transferido() {
        int selectedRow = jtbPedido.getSelectedRow();
        modeloPedido.setValueAt("Transferido", selectedRow, 3);
        objPedido.modificarEstatus("Transferido", ((Integer) modeloPedido.getValueAt(selectedRow, 0)).intValue());
    }

    private void completado() {

        int selectedRow = jtbPedido.getSelectedRow();
        modeloPedido.setValueAt("completado", selectedRow, 3);
        objPedido.modificarEstatus("Completado", ((Integer) modeloPedido.getValueAt(selectedRow, 0)).intValue());
    }

    private void enviado() {
        int selectedRow = jtbPedido.getSelectedRow();
        modeloPedido.setValueAt("Enviado", selectedRow, 3);
        objPedido.modificarEstatus("Enviado", ((Integer) modeloPedido.getValueAt(selectedRow, 0)).intValue());
    }

    private void detalle() {
        abrirOpcion(jpPrincipal, jpDetallePedido);
    }

    private void abrirVentana() {
        int selectedRow = jtbProductoVenta.getSelectedRow();
        objProductoVenta.setIdProducto((int) modeloProductoVenta.getValueAt(selectedRow,0));
        System.out.println(objProductoVenta.getIdProducto());
        frmAgregarProducto agregarPartida = new frmAgregarProducto(this, false, objProductoVenta.getIdProducto() ,this);
        agregarPartida.setVisible(true);
    }

    private void init() {
//          String path = "C:\\Users\\alexi\\Pictures\\Codev.png";
//  jLabel4.setIcon(null);
//    try {
//      BufferedImage img=ImageIO.read(new File(path));
//        jLabel4.setIcon(new ImageIcon(img));
//        jLabel4.revalidate();
//        jLabel4.repaint();
//        jLabel4.update(jLabel4.getGraphics());
//    } catch (IOException ex) {
//
//    }
    }

    public productosControlador getpcontrol() {
        return pcontrol;
    }

    public JFrame getFrame() {
        return this;
    }


    public JPanel getPanelPord() {
        return jpListaProd;
    }

    //Metodo de filtros   
    public void filtroEmpleado() {
        String buscarEmpleado = jtxBuscarEmpleado.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(String.valueOf(buscarEmpleado)));
    }

    public void filtroCliente() {
        String buscarCliente = jtxBuscarCliente.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(String.valueOf(buscarCliente)));
    }

    public void filtroProveedor() {
        String buscarProveedor = jtxBuscarProveedor.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(String.valueOf(buscarProveedor)));
    }

    public void filtroPedido() {
        String buscarPedido = jtxBuscarPedido.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(String.valueOf(buscarPedido)));
    }

    public void filtroProducto() {
        String buscarProductoVenta = jtxBuscarProductoVenta.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(String.valueOf(buscarProductoVenta)));
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jpMenu = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jbnCerrar = new javax.swing.JButton();
        jbnProducto = new javax.swing.JButton();
        jbnPedidos = new javax.swing.JButton();
        jbnDescuento = new javax.swing.JButton();
        jbnEmpleado = new javax.swing.JButton();
        jbnProveedor = new javax.swing.JButton();
        jbnVenta = new javax.swing.JButton();
        jbnClientes = new javax.swing.JButton();
        jbnCompra = new javax.swing.JButton();
        jbnRerportes = new javax.swing.JButton();
        jbnEmpresas = new javax.swing.JButton();
        jpInferior = new javax.swing.JPanel();
        jlbLogo = new javax.swing.JLabel();
        jpPedido = new javax.swing.JPanel();
        jpBarraSuperiorPedido = new javax.swing.JPanel();
        jlbTituloPedido = new javax.swing.JLabel();
        jtxBuscarPedido = new javax.swing.JTextField();
        jSeparator42 = new javax.swing.JSeparator();
        jbnCerrarPedido = new javax.swing.JButton();
        jbnRegresarPedido = new javax.swing.JButton();
        jlbBuscarPedido = new javax.swing.JLabel();
        jPMenuPedido = new javax.swing.JPanel();
        jlbLogoPrograma2 = new javax.swing.JLabel();
        jpBarraInferiorPedido = new javax.swing.JPanel();
        jlbIconoPedido = new javax.swing.JLabel();
        jcbxEstatusPedido = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtbPedido = new javax.swing.JTable();
        jlbEstatusPedido = new javax.swing.JLabel();
        jpCliente = new javax.swing.JPanel();
        jpBarraSuperiorCliente = new javax.swing.JPanel();
        jlbTituloCliente = new javax.swing.JLabel();
        jtxBuscarCliente = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jbnCerrar1 = new javax.swing.JButton();
        jbnRegresarCliente = new javax.swing.JButton();
        jlbBuscarCliente2 = new javax.swing.JLabel();
        jpBarraInferiorCliente = new javax.swing.JPanel();
        jbnActualizarCliente = new javax.swing.JButton();
        jbnAgregarCliente = new javax.swing.JButton();
        jbnEliminarCliente = new javax.swing.JButton();
        jlbNombreCliente = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jtxNombreCliente = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jtxFechaNacimiento = new javax.swing.JTextField();
        jlbFechaNacCliente = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jtxApellidosClientes = new javax.swing.JTextField();
        jlbTelefonoCliente = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jtxTelefonoCliente = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jlbApellidosCliente = new javax.swing.JLabel();
        jlbCalleyNumCliente = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jtxCalleyNumCliente = new javax.swing.JTextField();
        jlbColoniaCliente = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jtxColoniaCliente = new javax.swing.JTextField();
        jlbMunicipioCliente = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jtxMunicipioCliente = new javax.swing.JTextField();
        jlbCpCliente = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jtxCpCliente = new javax.swing.JTextField();
        jlbEstadoCliente = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jtxEstadoCliente = new javax.swing.JTextField();
        jlbContrasenaCliente = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jtxContrasenaCliente = new javax.swing.JTextField();
        jlbRfcCliente = new javax.swing.JLabel();
        jtxRfcCliente = new javax.swing.JTextField();
        jlbEmailCliente = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jtxEmailCliente = new javax.swing.JTextField();
        jlbIdCliente = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jtxIdCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlbNoComprasCliente = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jtxNoComprasCliente = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtbCliente = new javax.swing.JTable();
        jlbReferencialiente = new javax.swing.JLabel();
        jSeparator27 = new javax.swing.JSeparator();
        jtxReferenciaCliente = new javax.swing.JTextField();
        jlbNombreEmpleado2 = new javax.swing.JLabel();
        jcbxEmpresaCliente = new javax.swing.JComboBox<>();
        jpEmpleado = new javax.swing.JPanel();
        jpBarraSuperiorEmpleado = new javax.swing.JPanel();
        jlbTituloEmpleado = new javax.swing.JLabel();
        jtxBuscarEmpleado = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jbnCerrarEmpleado = new javax.swing.JButton();
        jbnRegresarEmpleado = new javax.swing.JButton();
        jlbBuscarEmpleado = new javax.swing.JLabel();
        jpBarraInferiorCliente1 = new javax.swing.JPanel();
        jbnActualizarEmpleado = new javax.swing.JButton();
        jbnAgregarEmpleado = new javax.swing.JButton();
        jbnEliminarEmpleado = new javax.swing.JButton();
        jlbNombreEmpleado = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jtxNombreEmpleado = new javax.swing.JTextField();
        jSeparator18 = new javax.swing.JSeparator();
        jtxFechaNacimientoEmpl = new javax.swing.JTextField();
        jlbFechaNacEmpleado = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        jtxApellidosEmpleado = new javax.swing.JTextField();
        jlbTelefonoEmpleado = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jtxTelefonoEmpleado = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        jlbApellidosEmpleado = new javax.swing.JLabel();
        jlbCalleyNumEmpleado = new javax.swing.JLabel();
        jSeparator22 = new javax.swing.JSeparator();
        jtxCalleyNumEmpleado = new javax.swing.JTextField();
        jlbColoniaEmpleado = new javax.swing.JLabel();
        jSeparator23 = new javax.swing.JSeparator();
        jtxColoniaEmpleado = new javax.swing.JTextField();
        jlbMunicipioEmpleado = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        jtxMunicipioEmpleado = new javax.swing.JTextField();
        jlbCpEmpleado = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        jtxCpEmpleado = new javax.swing.JTextField();
        jlbEstadoEmpleado = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        jtxEstadoEmpleado = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbEmpleado = new javax.swing.JTable();
        jlbRfcEmpleado = new javax.swing.JLabel();
        jtxRfcEmpleado = new javax.swing.JTextField();
        jlbEmailEmpleado = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jtxEmailEmpleado = new javax.swing.JTextField();
        jlbIdEmpleado = new javax.swing.JLabel();
        jSeparator29 = new javax.swing.JSeparator();
        jtxIdEmpleado = new javax.swing.JTextField();
        jlbIconoEmpleado = new javax.swing.JLabel();
        jlbUsuarioEmpleado = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        jtxUsuarioEmpleado = new javax.swing.JTextField();
        jlbContrasenaEmpleado = new javax.swing.JLabel();
        jSeparator32 = new javax.swing.JSeparator();
        jtxContrasenaEmpleado = new javax.swing.JTextField();
        jtxRfcCliente6 = new javax.swing.JTextField();
        jlbNssEmpleado = new javax.swing.JLabel();
        jSeparator30 = new javax.swing.JSeparator();
        jtxNssEmpleado = new javax.swing.JTextField();
        jlbReferenciasEmpleado = new javax.swing.JLabel();
        jSeparator56 = new javax.swing.JSeparator();
        jtxReferenciasEmpleado = new javax.swing.JTextField();
        jlbNombreEmpleado1 = new javax.swing.JLabel();
        jcbxEmpresaEmpleado = new javax.swing.JComboBox<>();
        jpEmpresa = new javax.swing.JPanel();
        jpBarraSuperiorEmpresa = new javax.swing.JPanel();
        jlbTituloEmpresa = new javax.swing.JLabel();
        jtxBuscarEmpresa = new javax.swing.JTextField();
        jSeparator33 = new javax.swing.JSeparator();
        jbnCerrarEmpresa = new javax.swing.JButton();
        jbnRegresarEmpresa = new javax.swing.JButton();
        jlbBuscarEmpresa = new javax.swing.JLabel();
        jPMenuEmpresa = new javax.swing.JPanel();
        jlbLogoPrograma1 = new javax.swing.JLabel();
        jlbIconoEmresa = new javax.swing.JLabel();
        jpOpcionTritonEmpresa = new javax.swing.JPanel();
        jlbOpcionNombreTriton = new javax.swing.JLabel();
        jpOpcionRedesEmpresa = new javax.swing.JPanel();
        jlbOpcionNombreRedes = new javax.swing.JLabel();
        jpBarraInferiorEmpresa = new javax.swing.JPanel();
        jbnActualizarEmpresa = new javax.swing.JButton();
        jbnAgregarEmpresa = new javax.swing.JButton();
        jbnEliminarCliente2 = new javax.swing.JButton();
        jlbNombreEmpresa = new javax.swing.JLabel();
        jSeparator34 = new javax.swing.JSeparator();
        jtxNombreEmpresa = new javax.swing.JTextField();
        jSeparator35 = new javax.swing.JSeparator();
        jtxDomicilioEmpresa = new javax.swing.JTextField();
        jlbDomicilioEmpresa = new javax.swing.JLabel();
        jSeparator36 = new javax.swing.JSeparator();
        jtxTelefonoEmpresa = new javax.swing.JTextField();
        jlbTelefonoEmpresa = new javax.swing.JLabel();
        jSeparator37 = new javax.swing.JSeparator();
        jtxRfcEmpresa = new javax.swing.JTextField();
        jlbRfcEmpresa = new javax.swing.JLabel();
        jSeparator38 = new javax.swing.JSeparator();
        jtxEmailEmpresa = new javax.swing.JTextField();
        jlbEmailEmpresa = new javax.swing.JLabel();
        jSeparator39 = new javax.swing.JSeparator();
        jtxLocalidadEmpresa = new javax.swing.JTextField();
        jlbLocalidadEmpresa = new javax.swing.JLabel();
        jSeparator40 = new javax.swing.JSeparator();
        jtxNumEmpleadosEmpresa = new javax.swing.JTextField();
        jlbNumEmpleadosEmpresa = new javax.swing.JLabel();
        jlbLogoEmpresa = new javax.swing.JLabel();
        jpProveedor = new javax.swing.JPanel();
        jpBarraSuperiorPedido1 = new javax.swing.JPanel();
        jlbTituloProveedor = new javax.swing.JLabel();
        jtxBuscarProveedor = new javax.swing.JTextField();
        jSeparator43 = new javax.swing.JSeparator();
        jbnCerrarProveedor = new javax.swing.JButton();
        jbnRegresarProveedor = new javax.swing.JButton();
        jlbBuscarProveedor = new javax.swing.JLabel();
        jpBarraInferiorPedido1 = new javax.swing.JPanel();
        jbnActualizarProveedor = new javax.swing.JButton();
        jbnAgregarProveedor = new javax.swing.JButton();
        jbnEliminarProveedor = new javax.swing.JButton();
        jlbNombreProveedor = new javax.swing.JLabel();
        jSeparator41 = new javax.swing.JSeparator();
        jtxNombreProveedor = new javax.swing.JTextField();
        jSeparator44 = new javax.swing.JSeparator();
        jtxDomicilioProveedor = new javax.swing.JTextField();
        jlbDomicilioProveedor = new javax.swing.JLabel();
        jSeparator45 = new javax.swing.JSeparator();
        jtxTelefonoProveedor = new javax.swing.JTextField();
        jlbTelefonoProveedor = new javax.swing.JLabel();
        jSeparator46 = new javax.swing.JSeparator();
        jtxRfcProveedor = new javax.swing.JTextField();
        jlbRfcProveedor = new javax.swing.JLabel();
        jSeparator47 = new javax.swing.JSeparator();
        jtxEmailProveedor = new javax.swing.JTextField();
        jlbEmailProveedor = new javax.swing.JLabel();
        jlbLogoEmpresaProveedor = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jlbIconoProveedor = new javax.swing.JLabel();
        jlbLogoProgramaProveedor = new javax.swing.JLabel();
        jlbIdProveedor = new javax.swing.JLabel();
        jSeparator48 = new javax.swing.JSeparator();
        jtxIdProveedor = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtbProveedor = new javax.swing.JTable();
        jpOpcionesyAsistencia = new javax.swing.JPanel();
        jpOpcionesContenido = new javax.swing.JPanel();
        jpOpcion1 = new javax.swing.JPanel();
        jbnCerrarOpciones1 = new javax.swing.JButton();
        jpPanelInformacion = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jpOpcionesPoliticas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jbnCerrarOpciones = new javax.swing.JButton();
        jpOpcionDescuento = new javax.swing.JPanel();
        jbnCerrarOpciones2 = new javax.swing.JButton();
        jpOpcionesMenu = new javax.swing.JPanel();
        jlbLogoProgramaProveedor1 = new javax.swing.JLabel();
        jpInformacionAsistencia = new javax.swing.JPanel();
        jlbOpcionNombreRedes1 = new javax.swing.JLabel();
        jpPoliticasAsistencia = new javax.swing.JPanel();
        jlbOpcionNombreRedes2 = new javax.swing.JLabel();
        jbnRegresarOpciones = new javax.swing.JButton();
        jlbIconoOpciones = new javax.swing.JLabel();
        jpProducto = new javax.swing.JPanel();
        jpBarraSuperiorProducto = new javax.swing.JPanel();
        jlbTituloProducto = new javax.swing.JLabel();
        jtxBuscarProducto = new javax.swing.JTextField();
        jSeparator49 = new javax.swing.JSeparator();
        jbnCerrarProducto = new javax.swing.JButton();
        jbnRegresarProducto = new javax.swing.JButton();
        jlbBuscarProducto = new javax.swing.JLabel();
        jpBarraInferiorProducto = new javax.swing.JPanel();
        jbnActualizarProducto = new javax.swing.JButton();
        jbnAgregarProveedor1 = new javax.swing.JButton();
        jbnEliminarProducto = new javax.swing.JButton();
        jlbLogoEmpresaProveedor1 = new javax.swing.JLabel();
        jpContenidoLogoProducto = new javax.swing.JPanel();
        jlbIconoProducto = new javax.swing.JLabel();
        jlbLogoProgramaProducto = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jpListaProd = new javax.swing.JPanel();
        jpVenta = new javax.swing.JPanel();
        jpPanelSuperiorVenta = new javax.swing.JPanel();
        jtxBuscarProductoVenta = new javax.swing.JTextField();
        jSeparator50 = new javax.swing.JSeparator();
        jlbBuscarPedido2 = new javax.swing.JLabel();
        jbnCerrarVenta = new javax.swing.JButton();
        jbnEliminarProducto2 = new javax.swing.JButton();
        jbnRegresarEmpleado1 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        jtbProductoVenta = new javax.swing.JTable();
        jScrollPane15 = new javax.swing.JScrollPane();
        jtbDetalleVenta = new javax.swing.JTable();
        jlbBuscarPedido4 = new javax.swing.JLabel();
        jcbxClienteVenta = new javax.swing.JComboBox<>();
        jlbClienteVenta = new javax.swing.JLabel();
        jtxTotalVenta = new javax.swing.JTextField();
        jlbIva = new javax.swing.JLabel();
        jSeparator54 = new javax.swing.JSeparator();
        jlbSignoTotal = new javax.swing.JLabel();
        jlbTotalVenta1 = new javax.swing.JLabel();
        jlbCambio = new javax.swing.JLabel();
        jtxIva = new javax.swing.JTextField();
        jtxSubtotalVenta = new javax.swing.JTextField();
        jlbTotalVenta3 = new javax.swing.JLabel();
        jtxCambio = new javax.swing.JTextField();
        jSeparator55 = new javax.swing.JSeparator();
        jlbPago = new javax.swing.JLabel();
        jSeparator61 = new javax.swing.JSeparator();
        jtxPagoPor1 = new javax.swing.JTextField();
        jpPanelInferiorVenta = new javax.swing.JPanel();
        jbnAgregarProveedor2 = new javax.swing.JButton();
        jbnCerrarSesionVenta = new javax.swing.JButton();
        jbnCancelarVenta = new javax.swing.JButton();
        jpPanelContenedor = new javax.swing.JPanel();
        jlbBuscarPedido3 = new javax.swing.JLabel();
        jpReportes = new javax.swing.JPanel();
        jpCompra = new javax.swing.JPanel();
        jPedido1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpGeneral = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jtbPedidogenral = new javax.swing.JTable();
        jpPestañaRecibido = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jtbPedidoRecibido = new javax.swing.JTable();
        jpPestañaTransferido = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jtbTransferido = new javax.swing.JTable();
        jpPestañaEnviado = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jtbPedido2 = new javax.swing.JTable();
        jpPestañaCompletado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Prueba = new javax.swing.JTable();
        jpBarraSuperiorPedido2 = new javax.swing.JPanel();
        jlbTituloPedido1 = new javax.swing.JLabel();
        jtxBuscarPedido1 = new javax.swing.JTextField();
        jSeparator57 = new javax.swing.JSeparator();
        jbnCerrarPedido1 = new javax.swing.JButton();
        jbnRegresarPedido1 = new javax.swing.JButton();
        jlbBuscarPedido1 = new javax.swing.JLabel();
        jpDescripcionPedido = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator58 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator59 = new javax.swing.JSeparator();
        jpDetallePedido = new javax.swing.JPanel();
        jpBarraInferiorPedido2 = new javax.swing.JPanel();
        jpBarraSuperiorPedido3 = new javax.swing.JPanel();
        jlbTituloPedido2 = new javax.swing.JLabel();
        jbnCerrarPedido2 = new javax.swing.JButton();
        jbnRegresarPedido2 = new javax.swing.JButton();
        jlbTituloPedido3 = new javax.swing.JLabel();
        jpPanelLogoDetallePedido = new javax.swing.JPanel();
        jlbIconoPedido1 = new javax.swing.JLabel();
        jlbLogoPrograma3 = new javax.swing.JLabel();
        jlbNombreProveedor1 = new javax.swing.JLabel();
        jSeparator51 = new javax.swing.JSeparator();
        jtxClientePedido = new javax.swing.JTextField();
        jSeparator52 = new javax.swing.JSeparator();
        jtxDomicilioPedido = new javax.swing.JTextField();
        jlbDomicilioProveedor1 = new javax.swing.JLabel();
        jSeparator53 = new javax.swing.JSeparator();
        jtxTelefonoPedido = new javax.swing.JTextField();
        jlbTelefonoPedido = new javax.swing.JLabel();
        jlbfolioPedido = new javax.swing.JLabel();
        jSeparator60 = new javax.swing.JSeparator();
        jtxfolioPedido = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        jtbPedido1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new java.awt.CardLayout());

        jpPrincipal.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpPrincipalMouseDragged(evt);
            }
        });
        jpPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpPrincipalMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpPrincipalMouseReleased(evt);
            }
        });
        jpPrincipal.setLayout(new java.awt.CardLayout());

        jpMenu.setBackground(new java.awt.Color(255, 255, 255));
        jpMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(22, 114, 185));

        jbnCerrar.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Close Window_50px_1.png"))); // NOI18N
        jbnCerrar.setBorderPainted(false);
        jbnCerrar.setContentAreaFilled(false);
        jbnCerrar.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Close Window_60px.png"))); // NOI18N
        jbnCerrar.setSelected(true);
        jbnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 1160, Short.MAX_VALUE)
                .addComponent(jbnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jbnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpMenu.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 40));

        jbnProducto.setBackground(new java.awt.Color(0, 51, 204));
        jbnProducto.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/71xN3Ijq4DL._128_.png"))); // NOI18N
        jbnProducto.setText("Producto");
        jbnProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnProducto.setBorderPainted(false);
        jbnProducto.setContentAreaFilled(false);
        jbnProducto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnProducto.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/71xN3Ijq4DL._150_.png"))); // NOI18N
        jbnProducto.setSelected(true);
        jbnProducto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnProductoActionPerformed(evt);
            }
        });
        jpMenu.add(jbnProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 190, 180));

        jbnPedidos.setBackground(new java.awt.Color(0, 51, 204));
        jbnPedidos.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pedido_128.png"))); // NOI18N
        jbnPedidos.setText("Pedidos");
        jbnPedidos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnPedidos.setBorderPainted(false);
        jbnPedidos.setContentAreaFilled(false);
        jbnPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnPedidos.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pedido_150.png"))); // NOI18N
        jbnPedidos.setSelected(true);
        jbnPedidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPedidosActionPerformed(evt);
            }
        });
        jpMenu.add(jbnPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 50, 190, 180));

        jbnDescuento.setBackground(new java.awt.Color(0, 51, 204));
        jbnDescuento.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnDescuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tool_128.png"))); // NOI18N
        jbnDescuento.setText("Opciones y asistencia");
        jbnDescuento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnDescuento.setBorderPainted(false);
        jbnDescuento.setContentAreaFilled(false);
        jbnDescuento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnDescuento.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tool_150.png"))); // NOI18N
        jbnDescuento.setSelected(true);
        jbnDescuento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnDescuentoActionPerformed(evt);
            }
        });
        jpMenu.add(jbnDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 230, 190, 180));

        jbnEmpleado.setBackground(new java.awt.Color(102, 153, 255));
        jbnEmpleado.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado_128.png"))); // NOI18N
        jbnEmpleado.setText("Empleado");
        jbnEmpleado.setBorderPainted(false);
        jbnEmpleado.setContentAreaFilled(false);
        jbnEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado_150.png"))); // NOI18N
        jbnEmpleado.setSelected(true);
        jbnEmpleado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEmpleadoActionPerformed(evt);
            }
        });
        jpMenu.add(jbnEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 190, 180));

        jbnProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnProveedor.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedor_128.png"))); // NOI18N
        jbnProveedor.setText("Proveedor");
        jbnProveedor.setBorderPainted(false);
        jbnProveedor.setContentAreaFilled(false);
        jbnProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedor_150.png"))); // NOI18N
        jbnProveedor.setSelected(true);
        jbnProveedor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnProveedorActionPerformed(evt);
            }
        });
        jpMenu.add(jbnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 190, 180));

        jbnVenta.setBackground(new java.awt.Color(0, 51, 204));
        jbnVenta.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/venta_128.png"))); // NOI18N
        jbnVenta.setText("Venta ");
        jbnVenta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnVenta.setBorderPainted(false);
        jbnVenta.setContentAreaFilled(false);
        jbnVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnVenta.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/venta_150.png"))); // NOI18N
        jbnVenta.setSelected(true);
        jbnVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnVentaActionPerformed(evt);
            }
        });
        jpMenu.add(jbnVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 190, 180));

        jbnClientes.setBackground(new java.awt.Color(0, 51, 204));
        jbnClientes.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cliente_128.png"))); // NOI18N
        jbnClientes.setText("Clientes");
        jbnClientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnClientes.setBorderPainted(false);
        jbnClientes.setContentAreaFilled(false);
        jbnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnClientes.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cliente_150.png"))); // NOI18N
        jbnClientes.setSelected(true);
        jbnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnClientesActionPerformed(evt);
            }
        });
        jpMenu.add(jbnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 190, 180));

        jbnCompra.setBackground(new java.awt.Color(0, 51, 204));
        jbnCompra.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/compra_128.png"))); // NOI18N
        jbnCompra.setText("Compra");
        jbnCompra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnCompra.setBorderPainted(false);
        jbnCompra.setContentAreaFilled(false);
        jbnCompra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnCompra.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Compra_150_fondo.png"))); // NOI18N
        jbnCompra.setSelected(true);
        jbnCompra.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCompraActionPerformed(evt);
            }
        });
        jpMenu.add(jbnCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 230, 190, 180));

        jbnRerportes.setBackground(new java.awt.Color(0, 51, 204));
        jbnRerportes.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnRerportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reporte_128.png"))); // NOI18N
        jbnRerportes.setText("Reportes");
        jbnRerportes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnRerportes.setBorderPainted(false);
        jbnRerportes.setContentAreaFilled(false);
        jbnRerportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnRerportes.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reporte_150.png"))); // NOI18N
        jbnRerportes.setSelected(true);
        jbnRerportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnRerportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRerportesActionPerformed(evt);
            }
        });
        jpMenu.add(jbnRerportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 410, 190, 180));

        jbnEmpresas.setBackground(new java.awt.Color(0, 51, 204));
        jbnEmpresas.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jbnEmpresas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empresa_128.png"))); // NOI18N
        jbnEmpresas.setText("Empresa");
        jbnEmpresas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jbnEmpresas.setBorderPainted(false);
        jbnEmpresas.setContentAreaFilled(false);
        jbnEmpresas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbnEmpresas.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empresa_150.png"))); // NOI18N
        jbnEmpresas.setSelected(true);
        jbnEmpresas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbnEmpresas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEmpresasActionPerformed(evt);
            }
        });
        jpMenu.add(jbnEmpresas, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, 190, 180));

        jpInferior.setBackground(new java.awt.Color(22, 114, 185));

        javax.swing.GroupLayout jpInferiorLayout = new javax.swing.GroupLayout(jpInferior);
        jpInferior.setLayout(jpInferiorLayout);
        jpInferiorLayout.setHorizontalGroup(
            jpInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        jpInferiorLayout.setVerticalGroup(
            jpInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jpMenu.add(jpInferior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 1200, -1));

        jlbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N
        jpMenu.add(jlbLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 240, 230));

        jpPrincipal.add(jpMenu, "card2");

        jpPedido.setBackground(new java.awt.Color(255, 255, 255));
        jpPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorPedido.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloPedido.setDisplayedMnemonic('b');
        jlbTituloPedido.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloPedido.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloPedido.setText("Gestor de pedidos");
        jlbTituloPedido.setToolTipText("");
        jlbTituloPedido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloPedido.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarPedido.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarPedido.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarPedido.setBorder(null);
        jtxBuscarPedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxBuscarPedidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxBuscarPedidoKeyTyped(evt);
            }
        });

        jSeparator42.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarPedido.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarPedido.setBorderPainted(false);
        jbnCerrarPedido.setContentAreaFilled(false);
        jbnCerrarPedido.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarPedido.setSelected(true);
        jbnCerrarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarPedidoActionPerformed(evt);
            }
        });

        jbnRegresarPedido.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarPedido.setBorderPainted(false);
        jbnRegresarPedido.setContentAreaFilled(false);
        jbnRegresarPedido.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarPedido.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarPedido.setSelected(true);
        jbnRegresarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarPedidoActionPerformed(evt);
            }
        });

        jlbBuscarPedido.setDisplayedMnemonic('b');
        jlbBuscarPedido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarPedido.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarPedido.setText("Buscar  ");
        jlbBuscarPedido.setToolTipText("");
        jlbBuscarPedido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarPedido.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorPedidoLayout = new javax.swing.GroupLayout(jpBarraSuperiorPedido);
        jpBarraSuperiorPedido.setLayout(jpBarraSuperiorPedidoLayout);
        jpBarraSuperiorPedidoLayout.setHorizontalGroup(
            jpBarraSuperiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedidoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(jlbBuscarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator42)
                    .addComponent(jtxBuscarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addComponent(jbnCerrarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorPedidoLayout.setVerticalGroup(
            jpBarraSuperiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedidoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarPedido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorPedidoLayout.createSequentialGroup()
                        .addComponent(jtxBuscarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator42)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorPedidoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloPedido)
                    .addComponent(jlbBuscarPedido))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorPedidoLayout.createSequentialGroup()
                .addComponent(jbnCerrarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpPedido.add(jpBarraSuperiorPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jPMenuPedido.setBackground(new java.awt.Color(153, 153, 153));
        jPMenuPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbLogoPrograma2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N
        jPMenuPedido.add(jlbLogoPrograma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 230, 220));

        jpPedido.add(jPMenuPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 280, 550));

        jpBarraInferiorPedido.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpBarraInferiorPedidoLayout = new javax.swing.GroupLayout(jpBarraInferiorPedido);
        jpBarraInferiorPedido.setLayout(jpBarraInferiorPedidoLayout);
        jpBarraInferiorPedidoLayout.setHorizontalGroup(
            jpBarraInferiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1196, Short.MAX_VALUE)
        );
        jpBarraInferiorPedidoLayout.setVerticalGroup(
            jpBarraInferiorPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        jpPedido.add(jpBarraInferiorPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbIconoPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pedido_128.png"))); // NOI18N
        jpPedido.add(jlbIconoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 140, 130));

        jcbxEstatusPedido.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbxEstatusPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una opción", "Recibido", "Enviado", "Transferido", "Completado" }));
        jcbxEstatusPedido.setBorder(null);
        jcbxEstatusPedido.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcbxEstatusPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxEstatusPedidoActionPerformed(evt);
            }
        });
        jpPedido.add(jcbxEstatusPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 380, 30));

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setBorder(null);
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbPedido.setAutoCreateRowSorter(true);
        jtbPedido.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbPedido.setModel(modeloPedido);
        jtbPedido.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbPedido.setGridColor(new java.awt.Color(255, 255, 255));
        jtbPedido.setRowHeight(25);
        jtbPedido.setRowMargin(8);
        jtbPedido.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane8.setViewportView(jtbPedido);

        jpPedido.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 880, 290));

        jlbEstatusPedido.setDisplayedMnemonic('b');
        jlbEstatusPedido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEstatusPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEstatusPedido.setText("Estatus pedido:");
        jlbEstatusPedido.setToolTipText("");
        jlbEstatusPedido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEstatusPedido.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpPedido.add(jlbEstatusPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 130, 30));

        jpPrincipal.add(jpPedido, "card3");

        jpCliente.setBackground(new java.awt.Color(255, 255, 255));
        jpCliente.setForeground(new java.awt.Color(51, 51, 51));
        jpCliente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorCliente.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloCliente.setDisplayedMnemonic('b');
        jlbTituloCliente.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloCliente.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloCliente.setText("Gestor de clientes");
        jlbTituloCliente.setToolTipText("");
        jlbTituloCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloCliente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarCliente.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarCliente.setBorder(null);
        jtxBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxBuscarClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxBuscarClienteKeyTyped(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrar1.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrar1.setBorderPainted(false);
        jbnCerrar1.setContentAreaFilled(false);
        jbnCerrar1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrar1.setSelected(true);
        jbnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrar1ActionPerformed(evt);
            }
        });

        jbnRegresarCliente.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarCliente.setBorderPainted(false);
        jbnRegresarCliente.setContentAreaFilled(false);
        jbnRegresarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarCliente.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarCliente.setSelected(true);
        jbnRegresarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarClienteActionPerformed(evt);
            }
        });

        jlbBuscarCliente2.setDisplayedMnemonic('b');
        jlbBuscarCliente2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarCliente2.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarCliente2.setText("Buscar  ");
        jlbBuscarCliente2.setToolTipText("");
        jlbBuscarCliente2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarCliente2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorClienteLayout = new javax.swing.GroupLayout(jpBarraSuperiorCliente);
        jpBarraSuperiorCliente.setLayout(jpBarraSuperiorClienteLayout);
        jpBarraSuperiorClienteLayout.setHorizontalGroup(
            jpBarraSuperiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jlbBuscarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jtxBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120)
                .addComponent(jbnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorClienteLayout.setVerticalGroup(
            jpBarraSuperiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                .addComponent(jbnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                        .addComponent(jtxBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator1)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBarraSuperiorClienteLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jlbTituloCliente))
                    .addComponent(jlbBuscarCliente2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpCliente.add(jpBarraSuperiorCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpBarraInferiorCliente.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbnActualizarCliente.setBackground(new java.awt.Color(145, 36, 36));
        jbnActualizarCliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnActualizarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jbnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_1.png"))); // NOI18N
        jbnActualizarCliente.setText("Actualizar cliente");
        jbnActualizarCliente.setBorderPainted(false);
        jbnActualizarCliente.setContentAreaFilled(false);
        jbnActualizarCliente.setEnabled(false);
        jbnActualizarCliente.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_2.png"))); // NOI18N
        jbnActualizarCliente.setSelected(true);
        jbnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnActualizarClienteActionPerformed(evt);
            }
        });

        jbnAgregarCliente.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarCliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarCliente.setText("Agregar cliente ");
        jbnAgregarCliente.setBorderPainted(false);
        jbnAgregarCliente.setContentAreaFilled(false);
        jbnAgregarCliente.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarCliente.setSelected(true);
        jbnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarClienteActionPerformed(evt);
            }
        });

        jbnEliminarCliente.setBackground(new java.awt.Color(145, 36, 36));
        jbnEliminarCliente.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnEliminarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px.png"))); // NOI18N
        jbnEliminarCliente.setText("Eliminar");
        jbnEliminarCliente.setBorderPainted(false);
        jbnEliminarCliente.setContentAreaFilled(false);
        jbnEliminarCliente.setEnabled(false);
        jbnEliminarCliente.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px_1.png"))); // NOI18N
        jbnEliminarCliente.setSelected(true);
        jbnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBarraInferiorClienteLayout = new javax.swing.GroupLayout(jpBarraInferiorCliente);
        jpBarraInferiorCliente.setLayout(jpBarraInferiorClienteLayout);
        jpBarraInferiorClienteLayout.setHorizontalGroup(
            jpBarraInferiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorClienteLayout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(jbnAgregarCliente)
                .addGap(112, 112, 112)
                .addComponent(jbnEliminarCliente)
                .addGap(125, 125, 125)
                .addComponent(jbnActualizarCliente)
                .addGap(150, 150, 150))
        );
        jpBarraInferiorClienteLayout.setVerticalGroup(
            jpBarraInferiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBarraInferiorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnActualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpCliente.add(jpBarraInferiorCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbNombreCliente.setDisplayedMnemonic('b');
        jlbNombreCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Contacts_25px.png"))); // NOI18N
        jlbNombreCliente.setText("Nombre(s):");
        jlbNombreCliente.setToolTipText("");
        jlbNombreCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 130, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 210, 10));

        jtxNombreCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNombreCliente.setForeground(new java.awt.Color(102, 102, 102));
        jtxNombreCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNombreCliente.setBorder(null);
        jpCliente.add(jtxNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 210, 30));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 210, 10));

        jtxFechaNacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxFechaNacimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxFechaNacimiento.setBorder(null);
        jpCliente.add(jtxFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 210, 30));

        jlbFechaNacCliente.setDisplayedMnemonic('b');
        jlbFechaNacCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbFechaNacCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbFechaNacCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Leave_22px_1.png"))); // NOI18N
        jlbFechaNacCliente.setText("Fecha de Nacimiento:");
        jlbFechaNacCliente.setToolTipText("");
        jlbFechaNacCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbFechaNacCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbFechaNacCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 200, 30));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, 210, 10));

        jtxApellidosClientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxApellidosClientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxApellidosClientes.setBorder(null);
        jpCliente.add(jtxApellidosClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 210, 30));

        jlbTelefonoCliente.setDisplayedMnemonic('b');
        jlbTelefonoCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbTelefonoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoCliente.setText("Telefono:");
        jlbTelefonoCliente.setToolTipText("");
        jlbTelefonoCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 120, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 180, 10));

        jtxTelefonoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxTelefonoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxTelefonoCliente.setBorder(null);
        jpCliente.add(jtxTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 180, 30));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 210, 10));

        jlbApellidosCliente.setDisplayedMnemonic('b');
        jlbApellidosCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbApellidosCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbApellidosCliente.setText("Apellidos:");
        jlbApellidosCliente.setToolTipText("");
        jlbApellidosCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbApellidosCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbApellidosCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 100, 30));

        jlbCalleyNumCliente.setDisplayedMnemonic('b');
        jlbCalleyNumCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbCalleyNumCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbCalleyNumCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbCalleyNumCliente.setText("Calle y número:");
        jlbCalleyNumCliente.setToolTipText("");
        jlbCalleyNumCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbCalleyNumCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbCalleyNumCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 30));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 210, 10));

        jtxCalleyNumCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxCalleyNumCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCalleyNumCliente.setBorder(null);
        jpCliente.add(jtxCalleyNumCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 220, 30));

        jlbColoniaCliente.setDisplayedMnemonic('b');
        jlbColoniaCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbColoniaCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbColoniaCliente.setText("Colonia:");
        jlbColoniaCliente.setToolTipText("");
        jlbColoniaCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbColoniaCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbColoniaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 80, 20));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 170, 10));

        jtxColoniaCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxColoniaCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxColoniaCliente.setBorder(null);
        jpCliente.add(jtxColoniaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 190, 170, 30));

        jlbMunicipioCliente.setDisplayedMnemonic('b');
        jlbMunicipioCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbMunicipioCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbMunicipioCliente.setText("Municipio:");
        jlbMunicipioCliente.setToolTipText("");
        jlbMunicipioCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbMunicipioCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbMunicipioCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 90, -1));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 180, 10));

        jtxMunicipioCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxMunicipioCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxMunicipioCliente.setBorder(null);
        jpCliente.add(jtxMunicipioCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 190, 30));

        jlbCpCliente.setDisplayedMnemonic('b');
        jlbCpCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbCpCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbCpCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Pin Code_25px_1.png"))); // NOI18N
        jlbCpCliente.setText("CP:");
        jlbCpCliente.setToolTipText("");
        jlbCpCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbCpCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbCpCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 200, 70, -1));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 220, 120, 10));

        jtxCpCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxCpCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCpCliente.setBorder(null);
        jpCliente.add(jtxCpCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 190, 120, 30));

        jlbEstadoCliente.setDisplayedMnemonic('b');
        jlbEstadoCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEstadoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEstadoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Germany Map_25px.png"))); // NOI18N
        jlbEstadoCliente.setText("Estado:");
        jlbEstadoCliente.setToolTipText("");
        jlbEstadoCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEstadoCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbEstadoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 100, 20));

        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 140, 10));

        jtxEstadoCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEstadoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEstadoCliente.setBorder(null);
        jpCliente.add(jtxEstadoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 140, 30));

        jlbContrasenaCliente.setDisplayedMnemonic('b');
        jlbContrasenaCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbContrasenaCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbContrasenaCliente.setText("Contraseña");
        jlbContrasenaCliente.setToolTipText("");
        jlbContrasenaCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbContrasenaCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbContrasenaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, 110, 20));

        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 170, 150, 10));

        jtxContrasenaCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxContrasenaCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxContrasenaCliente.setBorder(null);
        jpCliente.add(jtxContrasenaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 140, 140, 30));

        jlbRfcCliente.setDisplayedMnemonic('b');
        jlbRfcCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbRfcCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcCliente.setText("RFC:");
        jlbRfcCliente.setToolTipText("");
        jlbRfcCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbRfcCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 60, 20));

        jtxRfcCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxRfcCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxRfcCliente.setBorder(null);
        jpCliente.add(jtxRfcCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 210, 30));

        jlbEmailCliente.setDisplayedMnemonic('b');
        jlbEmailCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEmailCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEmailCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mail_25px.png"))); // NOI18N
        jlbEmailCliente.setText("E-mail:");
        jlbEmailCliente.setToolTipText("");
        jlbEmailCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEmailCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbEmailCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 100, -1));

        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 170, 210, 10));

        jtxEmailCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEmailCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEmailCliente.setBorder(null);
        jpCliente.add(jtxEmailCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 210, 30));

        jlbIdCliente.setDisplayedMnemonic('b');
        jlbIdCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbIdCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIdCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ID Card_25px_2.png"))); // NOI18N
        jlbIdCliente.setText("ID cliente:");
        jlbIdCliente.setToolTipText("");
        jlbIdCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbIdCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 120, -1));

        jSeparator15.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 120, 10));

        jtxIdCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxIdCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxIdCliente.setBorder(null);
        jtxIdCliente.setEnabled(false);
        jpCliente.add(jtxIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 120, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cliente_128.png"))); // NOI18N
        jpCliente.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 120, 130));

        jlbNoComprasCliente.setDisplayedMnemonic('b');
        jlbNoComprasCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNoComprasCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNoComprasCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Shopping Cart_25px_1.png"))); // NOI18N
        jlbNoComprasCliente.setText("No. Compras:");
        jlbNoComprasCliente.setToolTipText("");
        jlbNoComprasCliente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNoComprasCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbNoComprasCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 70, 140, -1));

        jSeparator16.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 90, 100, 10));

        jtxNoComprasCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNoComprasCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNoComprasCliente.setBorder(null);
        jtxNoComprasCliente.setEnabled(false);
        jpCliente.add(jtxNoComprasCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 70, 100, 20));

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(null);
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbCliente.setAutoCreateRowSorter(true);
        jtbCliente.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbCliente.setModel(modeloCliente);
        jtbCliente.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbCliente.setGridColor(new java.awt.Color(255, 255, 255));
        jtbCliente.setRowHeight(25);
        jtbCliente.setRowMargin(8);
        jtbCliente.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane6.setViewportView(jtbCliente);

        jpCliente.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 1020, 280));

        jlbReferencialiente.setDisplayedMnemonic('b');
        jlbReferencialiente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbReferencialiente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbReferencialiente.setText("Referencias:");
        jlbReferencialiente.setToolTipText("");
        jlbReferencialiente.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbReferencialiente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbReferencialiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 100, -1));

        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        jpCliente.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 220, 180, 10));

        jtxReferenciaCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxReferenciaCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxReferenciaCliente.setBorder(null);
        jpCliente.add(jtxReferenciaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 190, 190, 30));

        jlbNombreEmpleado2.setDisplayedMnemonic('b');
        jlbNombreEmpleado2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreEmpleado2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreEmpleado2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Business Building_25px_5.png"))); // NOI18N
        jlbNombreEmpleado2.setText("Empresa:");
        jlbNombreEmpleado2.setToolTipText("");
        jlbNombreEmpleado2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreEmpleado2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpCliente.add(jlbNombreEmpleado2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, 130, -1));

        jcbxEmpresaCliente.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbxEmpresaCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una opción", "Redes y Equipos Pesqueros S.A  De C.V", "Redes y Accesorios Pesqueros Tritón S. de R.L. de C.V" }));
        jcbxEmpresaCliente.setBorder(null);
        jcbxEmpresaCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpCliente.add(jcbxEmpresaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 250, 430, 30));

        jpPrincipal.add(jpCliente, "card3");

        jpEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jpEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        jpEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorEmpleado.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorEmpleado.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloEmpleado.setDisplayedMnemonic('b');
        jlbTituloEmpleado.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloEmpleado.setText("Gestor de empleados");
        jlbTituloEmpleado.setToolTipText("");
        jlbTituloEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarEmpleado.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarEmpleado.setBorder(null);
        jtxBuscarEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxBuscarEmpleadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxBuscarEmpleadoKeyTyped(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarEmpleado.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarEmpleado.setBorderPainted(false);
        jbnCerrarEmpleado.setContentAreaFilled(false);
        jbnCerrarEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarEmpleado.setSelected(true);
        jbnCerrarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarEmpleadoActionPerformed(evt);
            }
        });

        jbnRegresarEmpleado.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarEmpleado.setBorderPainted(false);
        jbnRegresarEmpleado.setContentAreaFilled(false);
        jbnRegresarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarEmpleado.setSelected(true);
        jbnRegresarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarEmpleadoActionPerformed(evt);
            }
        });

        jlbBuscarEmpleado.setDisplayedMnemonic('b');
        jlbBuscarEmpleado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarEmpleado.setText("Buscar  ");
        jlbBuscarEmpleado.setToolTipText("");
        jlbBuscarEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorEmpleadoLayout = new javax.swing.GroupLayout(jpBarraSuperiorEmpleado);
        jpBarraSuperiorEmpleado.setLayout(jpBarraSuperiorEmpleadoLayout);
        jpBarraSuperiorEmpleadoLayout.setHorizontalGroup(
            jpBarraSuperiorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorEmpleadoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloEmpleado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(jlbBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator3)
                    .addComponent(jtxBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addComponent(jbnCerrarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpBarraSuperiorEmpleadoLayout.setVerticalGroup(
            jpBarraSuperiorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorEmpleadoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarEmpleado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorEmpleadoLayout.createSequentialGroup()
                        .addComponent(jtxBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator3)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorEmpleadoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloEmpleado)
                    .addComponent(jlbBuscarEmpleado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpBarraSuperiorEmpleadoLayout.createSequentialGroup()
                .addComponent(jbnCerrarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpEmpleado.add(jpBarraSuperiorEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpBarraInferiorCliente1.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorCliente1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbnActualizarEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jbnActualizarEmpleado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnActualizarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jbnActualizarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_1.png"))); // NOI18N
        jbnActualizarEmpleado.setText("Actualizar empleado");
        jbnActualizarEmpleado.setBorderPainted(false);
        jbnActualizarEmpleado.setContentAreaFilled(false);
        jbnActualizarEmpleado.setEnabled(false);
        jbnActualizarEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_2.png"))); // NOI18N
        jbnActualizarEmpleado.setSelected(true);
        jbnActualizarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnActualizarEmpleadoActionPerformed(evt);
            }
        });

        jbnAgregarEmpleado.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarEmpleado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarEmpleado.setText("Agregar empleado ");
        jbnAgregarEmpleado.setBorderPainted(false);
        jbnAgregarEmpleado.setContentAreaFilled(false);
        jbnAgregarEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarEmpleado.setSelected(true);
        jbnAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarEmpleadoActionPerformed(evt);
            }
        });

        jbnEliminarEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        jbnEliminarEmpleado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnEliminarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px.png"))); // NOI18N
        jbnEliminarEmpleado.setText("Eliminar");
        jbnEliminarEmpleado.setBorderPainted(false);
        jbnEliminarEmpleado.setContentAreaFilled(false);
        jbnEliminarEmpleado.setEnabled(false);
        jbnEliminarEmpleado.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px_1.png"))); // NOI18N
        jbnEliminarEmpleado.setSelected(true);
        jbnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBarraInferiorCliente1Layout = new javax.swing.GroupLayout(jpBarraInferiorCliente1);
        jpBarraInferiorCliente1.setLayout(jpBarraInferiorCliente1Layout);
        jpBarraInferiorCliente1Layout.setHorizontalGroup(
            jpBarraInferiorCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorCliente1Layout.createSequentialGroup()
                .addContainerGap(188, Short.MAX_VALUE)
                .addComponent(jbnAgregarEmpleado)
                .addGap(112, 112, 112)
                .addComponent(jbnEliminarEmpleado)
                .addGap(125, 125, 125)
                .addComponent(jbnActualizarEmpleado)
                .addGap(150, 150, 150))
        );
        jpBarraInferiorCliente1Layout.setVerticalGroup(
            jpBarraInferiorCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorCliente1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBarraInferiorCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnActualizarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbnEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpEmpleado.add(jpBarraInferiorCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbNombreEmpleado.setDisplayedMnemonic('b');
        jlbNombreEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Business Building_25px_5.png"))); // NOI18N
        jlbNombreEmpleado.setText("Empresa:");
        jlbNombreEmpleado.setToolTipText("");
        jlbNombreEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbNombreEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 130, -1));

        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 210, 10));

        jtxNombreEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNombreEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxNombreEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNombreEmpleado.setBorder(null);
        jpEmpleado.add(jtxNombreEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 210, 30));

        jSeparator18.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, 210, 10));

        jtxFechaNacimientoEmpl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxFechaNacimientoEmpl.setForeground(new java.awt.Color(102, 102, 102));
        jtxFechaNacimientoEmpl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxFechaNacimientoEmpl.setBorder(null);
        jpEmpleado.add(jtxFechaNacimientoEmpl, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, 210, 30));

        jlbFechaNacEmpleado.setDisplayedMnemonic('b');
        jlbFechaNacEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbFechaNacEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbFechaNacEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Leave_22px_1.png"))); // NOI18N
        jlbFechaNacEmpleado.setText("Fecha de Nacimiento:");
        jlbFechaNacEmpleado.setToolTipText("");
        jlbFechaNacEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbFechaNacEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbFechaNacEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, 200, 30));

        jSeparator19.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 190, 210, 10));

        jtxApellidosEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxApellidosEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxApellidosEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxApellidosEmpleado.setBorder(null);
        jpEmpleado.add(jtxApellidosEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 210, 30));

        jlbTelefonoEmpleado.setDisplayedMnemonic('b');
        jlbTelefonoEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbTelefonoEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoEmpleado.setText("Teléfono:");
        jlbTelefonoEmpleado.setToolTipText("");
        jlbTelefonoEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbTelefonoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 120, -1));

        jSeparator20.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, 180, 10));

        jtxTelefonoEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxTelefonoEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxTelefonoEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxTelefonoEmpleado.setBorder(null);
        jpEmpleado.add(jtxTelefonoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 180, 30));

        jSeparator21.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 140, 210, 10));

        jlbApellidosEmpleado.setDisplayedMnemonic('b');
        jlbApellidosEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbApellidosEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbApellidosEmpleado.setText("Apellidos:");
        jlbApellidosEmpleado.setToolTipText("");
        jlbApellidosEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbApellidosEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbApellidosEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 100, 30));

        jlbCalleyNumEmpleado.setDisplayedMnemonic('b');
        jlbCalleyNumEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbCalleyNumEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbCalleyNumEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbCalleyNumEmpleado.setText("Calle y número:");
        jlbCalleyNumEmpleado.setToolTipText("");
        jlbCalleyNumEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbCalleyNumEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbCalleyNumEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 160, 30));

        jSeparator22.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 270, 10));

        jtxCalleyNumEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxCalleyNumEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxCalleyNumEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCalleyNumEmpleado.setBorder(null);
        jpEmpleado.add(jtxCalleyNumEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 280, 30));

        jlbColoniaEmpleado.setDisplayedMnemonic('b');
        jlbColoniaEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbColoniaEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbColoniaEmpleado.setText("Colonia:");
        jlbColoniaEmpleado.setToolTipText("");
        jlbColoniaEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbColoniaEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbColoniaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 80, 30));

        jSeparator23.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, 170, 10));

        jtxColoniaEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxColoniaEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxColoniaEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxColoniaEmpleado.setBorder(null);
        jpEmpleado.add(jtxColoniaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 170, 30));

        jlbMunicipioEmpleado.setDisplayedMnemonic('b');
        jlbMunicipioEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbMunicipioEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbMunicipioEmpleado.setText("Municipio:");
        jlbMunicipioEmpleado.setToolTipText("");
        jlbMunicipioEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbMunicipioEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbMunicipioEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 90, -1));

        jSeparator24.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 180, 10));

        jtxMunicipioEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxMunicipioEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxMunicipioEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxMunicipioEmpleado.setBorder(null);
        jpEmpleado.add(jtxMunicipioEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 190, 30));

        jlbCpEmpleado.setDisplayedMnemonic('b');
        jlbCpEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jlbCpEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbCpEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Pin Code_25px_1.png"))); // NOI18N
        jlbCpEmpleado.setText("CP:");
        jlbCpEmpleado.setToolTipText("");
        jlbCpEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbCpEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbCpEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, 70, -1));

        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 330, 120, 10));

        jtxCpEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxCpEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxCpEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCpEmpleado.setBorder(null);
        jpEmpleado.add(jtxCpEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 120, 30));

        jlbEstadoEmpleado.setDisplayedMnemonic('b');
        jlbEstadoEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEstadoEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEstadoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Germany Map_25px.png"))); // NOI18N
        jlbEstadoEmpleado.setText("Estado:");
        jlbEstadoEmpleado.setToolTipText("");
        jlbEstadoEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEstadoEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbEstadoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 100, 30));

        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 330, 140, 10));

        jtxEstadoEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEstadoEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxEstadoEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEstadoEmpleado.setBorder(null);
        jpEmpleado.add(jtxEstadoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, 140, 30));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbEmpleado.setAutoCreateRowSorter(true);
        jtbEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbEmpleado.setModel(modeloEmpleado);
        jtbEmpleado.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbEmpleado.setGridColor(new java.awt.Color(255, 255, 255));
        jtbEmpleado.setRowHeight(25);
        jtbEmpleado.setRowMargin(8);
        jtbEmpleado.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane2.setViewportView(jtbEmpleado);

        jpEmpleado.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 1030, 240));

        jlbRfcEmpleado.setDisplayedMnemonic('b');
        jlbRfcEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbRfcEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcEmpleado.setText("RFC:");
        jlbRfcEmpleado.setToolTipText("");
        jlbRfcEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbRfcEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, 60, -1));

        jtxRfcEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxRfcEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxRfcEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxRfcEmpleado.setBorder(null);
        jpEmpleado.add(jtxRfcEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 110, 210, 30));

        jlbEmailEmpleado.setDisplayedMnemonic('b');
        jlbEmailEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEmailEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEmailEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mail_25px.png"))); // NOI18N
        jlbEmailEmpleado.setText("E-mail:");
        jlbEmailEmpleado.setToolTipText("");
        jlbEmailEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEmailEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbEmailEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 90, -1));

        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 210, 10));

        jtxEmailEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEmailEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxEmailEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEmailEmpleado.setBorder(null);
        jpEmpleado.add(jtxEmailEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, 210, 30));

        jlbIdEmpleado.setDisplayedMnemonic('b');
        jlbIdEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbIdEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIdEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ID Card_25px_2.png"))); // NOI18N
        jlbIdEmpleado.setText("ID empleado:");
        jlbIdEmpleado.setToolTipText("");
        jlbIdEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbIdEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbIdEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 140, -1));

        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 120, 10));

        jtxIdEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxIdEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxIdEmpleado.setBorder(null);
        jtxIdEmpleado.setEnabled(false);
        jpEmpleado.add(jtxIdEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 120, 20));

        jlbIconoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado_128.png"))); // NOI18N
        jpEmpleado.add(jlbIconoEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 120, 130));

        jlbUsuarioEmpleado.setDisplayedMnemonic('b');
        jlbUsuarioEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbUsuarioEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbUsuarioEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Male User_25px_1.png"))); // NOI18N
        jlbUsuarioEmpleado.setText("Usuario:");
        jlbUsuarioEmpleado.setToolTipText("");
        jlbUsuarioEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbUsuarioEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbUsuarioEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 100, -1));

        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 140, 10));

        jtxUsuarioEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxUsuarioEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxUsuarioEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxUsuarioEmpleado.setBorder(null);
        jpEmpleado.add(jtxUsuarioEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 130, 30));

        jlbContrasenaEmpleado.setDisplayedMnemonic('b');
        jlbContrasenaEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbContrasenaEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbContrasenaEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Forgot Password_25px.png"))); // NOI18N
        jlbContrasenaEmpleado.setText("Contraseña:");
        jlbContrasenaEmpleado.setToolTipText("");
        jlbContrasenaEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbContrasenaEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbContrasenaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 210, 130, -1));

        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 230, 180, 10));

        jtxContrasenaEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxContrasenaEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxContrasenaEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxContrasenaEmpleado.setBorder(null);
        jpEmpleado.add(jtxContrasenaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 200, 180, 30));

        jtxRfcCliente6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxRfcCliente6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxRfcCliente6.setBorder(null);
        jpEmpleado.add(jtxRfcCliente6, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 200, 210, 30));

        jlbNssEmpleado.setDisplayedMnemonic('b');
        jlbNssEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNssEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNssEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbNssEmpleado.setText("NSS");
        jlbNssEmpleado.setToolTipText("");
        jlbNssEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNssEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbNssEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 90, -1));

        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 170, 10));

        jtxNssEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNssEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxNssEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNssEmpleado.setBorder(null);
        jpEmpleado.add(jtxNssEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 170, 30));

        jlbReferenciasEmpleado.setDisplayedMnemonic('b');
        jlbReferenciasEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbReferenciasEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbReferenciasEmpleado.setText("Referencias:");
        jlbReferenciasEmpleado.setToolTipText("");
        jlbReferenciasEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbReferenciasEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbReferenciasEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 260, 120, -1));

        jSeparator56.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpleado.add(jSeparator56, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 280, 180, 10));

        jtxReferenciasEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxReferenciasEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        jtxReferenciasEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxReferenciasEmpleado.setBorder(null);
        jpEmpleado.add(jtxReferenciasEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 250, 190, 30));

        jlbNombreEmpleado1.setDisplayedMnemonic('b');
        jlbNombreEmpleado1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreEmpleado1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreEmpleado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Contacts_25px.png"))); // NOI18N
        jlbNombreEmpleado1.setText("Nombre(s):");
        jlbNombreEmpleado1.setToolTipText("");
        jlbNombreEmpleado1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreEmpleado1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpleado.add(jlbNombreEmpleado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 130, -1));

        jcbxEmpresaEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbxEmpresaEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona una opción", "Redes y Equipos Pesqueros S.A  De C.V", "Redes y Accesorios Pesqueros Tritón S. de R.L. de C.V" }));
        jcbxEmpresaEmpleado.setBorder(null);
        jcbxEmpresaEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpEmpleado.add(jcbxEmpresaEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 390, 30));

        jpPrincipal.add(jpEmpleado, "card3");

        jpEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        jpEmpresa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorEmpresa.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorEmpresa.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloEmpresa.setDisplayedMnemonic('b');
        jlbTituloEmpresa.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloEmpresa.setText("Gestor de empresas");
        jlbTituloEmpresa.setToolTipText("");
        jlbTituloEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarEmpresa.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarEmpresa.setBorder(null);

        jSeparator33.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarEmpresa.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarEmpresa.setBorderPainted(false);
        jbnCerrarEmpresa.setContentAreaFilled(false);
        jbnCerrarEmpresa.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarEmpresa.setSelected(true);
        jbnCerrarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarEmpresaActionPerformed(evt);
            }
        });

        jbnRegresarEmpresa.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarEmpresa.setBorderPainted(false);
        jbnRegresarEmpresa.setContentAreaFilled(false);
        jbnRegresarEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarEmpresa.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarEmpresa.setSelected(true);
        jbnRegresarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarEmpresaActionPerformed(evt);
            }
        });

        jlbBuscarEmpresa.setDisplayedMnemonic('b');
        jlbBuscarEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarEmpresa.setText("Buscar  ");
        jlbBuscarEmpresa.setToolTipText("");
        jlbBuscarEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorEmpresaLayout = new javax.swing.GroupLayout(jpBarraSuperiorEmpresa);
        jpBarraSuperiorEmpresa.setLayout(jpBarraSuperiorEmpresaLayout);
        jpBarraSuperiorEmpresaLayout.setHorizontalGroup(
            jpBarraSuperiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorEmpresaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(jlbBuscarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator33)
                    .addComponent(jtxBuscarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addComponent(jbnCerrarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorEmpresaLayout.setVerticalGroup(
            jpBarraSuperiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorEmpresaLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarEmpresa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorEmpresaLayout.createSequentialGroup()
                        .addComponent(jtxBuscarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator33)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorEmpresaLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloEmpresa)
                    .addComponent(jlbBuscarEmpresa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorEmpresaLayout.createSequentialGroup()
                .addComponent(jbnCerrarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpEmpresa.add(jpBarraSuperiorEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jPMenuEmpresa.setBackground(new java.awt.Color(204, 204, 204));
        jPMenuEmpresa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbLogoPrograma1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N
        jPMenuEmpresa.add(jlbLogoPrograma1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 285, 185));

        jlbIconoEmresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empresa_128.png"))); // NOI18N
        jPMenuEmpresa.add(jlbIconoEmresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 120, 130));

        jpOpcionTritonEmpresa.setBackground(new java.awt.Color(225, 225, 225));
        jpOpcionTritonEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpOpcionTritonEmpresaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpOpcionTritonEmpresaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpOpcionTritonEmpresaMouseExited(evt);
            }
        });

        jlbOpcionNombreTriton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jlbOpcionNombreTriton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbOpcionNombreTriton.setText("Redes y Accesorios Pesqueros Tritón");

        javax.swing.GroupLayout jpOpcionTritonEmpresaLayout = new javax.swing.GroupLayout(jpOpcionTritonEmpresa);
        jpOpcionTritonEmpresa.setLayout(jpOpcionTritonEmpresaLayout);
        jpOpcionTritonEmpresaLayout.setHorizontalGroup(
            jpOpcionTritonEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOpcionTritonEmpresaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jlbOpcionNombreTriton)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jpOpcionTritonEmpresaLayout.setVerticalGroup(
            jpOpcionTritonEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcionTritonEmpresaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlbOpcionNombreTriton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPMenuEmpresa.add(jpOpcionTritonEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 310, 40));

        jpOpcionRedesEmpresa.setBackground(new java.awt.Color(225, 225, 225));
        jpOpcionRedesEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpOpcionRedesEmpresaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpOpcionRedesEmpresaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpOpcionRedesEmpresaMouseExited(evt);
            }
        });

        jlbOpcionNombreRedes.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jlbOpcionNombreRedes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbOpcionNombreRedes.setText("Redes y Equipos Pesqueros ");

        javax.swing.GroupLayout jpOpcionRedesEmpresaLayout = new javax.swing.GroupLayout(jpOpcionRedesEmpresa);
        jpOpcionRedesEmpresa.setLayout(jpOpcionRedesEmpresaLayout);
        jpOpcionRedesEmpresaLayout.setHorizontalGroup(
            jpOpcionRedesEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOpcionRedesEmpresaLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jlbOpcionNombreRedes)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jpOpcionRedesEmpresaLayout.setVerticalGroup(
            jpOpcionRedesEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcionRedesEmpresaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlbOpcionNombreRedes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPMenuEmpresa.add(jpOpcionRedesEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 310, -1));

        jpEmpresa.add(jPMenuEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 310, 550));

        jpBarraInferiorEmpresa.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorEmpresa.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbnActualizarEmpresa.setBackground(new java.awt.Color(145, 36, 36));
        jbnActualizarEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnActualizarEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        jbnActualizarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_1.png"))); // NOI18N
        jbnActualizarEmpresa.setText("Actualizar empresa");
        jbnActualizarEmpresa.setBorderPainted(false);
        jbnActualizarEmpresa.setContentAreaFilled(false);
        jbnActualizarEmpresa.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_2.png"))); // NOI18N
        jbnActualizarEmpresa.setSelected(true);
        jbnActualizarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnActualizarEmpresaActionPerformed(evt);
            }
        });

        jbnAgregarEmpresa.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarEmpresa.setText("Agregar empresa ");
        jbnAgregarEmpresa.setBorderPainted(false);
        jbnAgregarEmpresa.setContentAreaFilled(false);
        jbnAgregarEmpresa.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarEmpresa.setSelected(true);
        jbnAgregarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarEmpresaActionPerformed(evt);
            }
        });

        jbnEliminarCliente2.setBackground(new java.awt.Color(145, 36, 36));
        jbnEliminarCliente2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnEliminarCliente2.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px.png"))); // NOI18N
        jbnEliminarCliente2.setText("Eliminar");
        jbnEliminarCliente2.setBorderPainted(false);
        jbnEliminarCliente2.setContentAreaFilled(false);
        jbnEliminarCliente2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px_1.png"))); // NOI18N
        jbnEliminarCliente2.setSelected(true);
        jbnEliminarCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarCliente2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBarraInferiorEmpresaLayout = new javax.swing.GroupLayout(jpBarraInferiorEmpresa);
        jpBarraInferiorEmpresa.setLayout(jpBarraInferiorEmpresaLayout);
        jpBarraInferiorEmpresaLayout.setHorizontalGroup(
            jpBarraInferiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorEmpresaLayout.createSequentialGroup()
                .addContainerGap(208, Short.MAX_VALUE)
                .addComponent(jbnAgregarEmpresa)
                .addGap(112, 112, 112)
                .addComponent(jbnEliminarCliente2)
                .addGap(125, 125, 125)
                .addComponent(jbnActualizarEmpresa)
                .addGap(150, 150, 150))
        );
        jpBarraInferiorEmpresaLayout.setVerticalGroup(
            jpBarraInferiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorEmpresaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBarraInferiorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnActualizarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnAgregarEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbnEliminarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpEmpresa.add(jpBarraInferiorEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbNombreEmpresa.setDisplayedMnemonic('b');
        jlbNombreEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbNombreEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Business Building_25px_5.png"))); // NOI18N
        jlbNombreEmpresa.setText("Nombre:");
        jlbNombreEmpresa.setToolTipText("");
        jlbNombreEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbNombreEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 130, -1));

        jSeparator34.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 460, 10));

        jtxNombreEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNombreEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxNombreEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNombreEmpresa.setBorder(null);
        jpEmpresa.add(jtxNombreEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 450, 30));

        jSeparator35.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 580, 10));

        jtxDomicilioEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxDomicilioEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxDomicilioEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxDomicilioEmpresa.setBorder(null);
        jpEmpresa.add(jtxDomicilioEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 580, 30));

        jlbDomicilioEmpresa.setDisplayedMnemonic('d');
        jlbDomicilioEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbDomicilioEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbDomicilioEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbDomicilioEmpresa.setText("Domicilio:");
        jlbDomicilioEmpresa.setToolTipText("");
        jlbDomicilioEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbDomicilioEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbDomicilioEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 120, 20));

        jSeparator36.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator36, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 260, 10));

        jtxTelefonoEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxTelefonoEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxTelefonoEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxTelefonoEmpresa.setBorder(null);
        jpEmpresa.add(jtxTelefonoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 240, 30));

        jlbTelefonoEmpresa.setDisplayedMnemonic('b');
        jlbTelefonoEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbTelefonoEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoEmpresa.setText("Telefono:");
        jlbTelefonoEmpresa.setToolTipText("");
        jlbTelefonoEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbTelefonoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 120, 30));

        jSeparator37.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 230, 260, 10));

        jtxRfcEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxRfcEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxRfcEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxRfcEmpresa.setBorder(null);
        jpEmpresa.add(jtxRfcEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 200, 240, 30));

        jlbRfcEmpresa.setDisplayedMnemonic('b');
        jlbRfcEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbRfcEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcEmpresa.setText("RFC:");
        jlbRfcEmpresa.setToolTipText("");
        jlbRfcEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbRfcEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, 70, 20));

        jSeparator38.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator38, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 260, 10));

        jtxEmailEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEmailEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxEmailEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEmailEmpresa.setBorder(null);
        jpEmpresa.add(jtxEmailEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 240, 30));

        jlbEmailEmpresa.setDisplayedMnemonic('b');
        jlbEmailEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbEmailEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEmailEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mail_25px.png"))); // NOI18N
        jlbEmailEmpresa.setText("Email:");
        jlbEmailEmpresa.setToolTipText("");
        jlbEmailEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEmailEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbEmailEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 100, 20));

        jSeparator39.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator39, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 290, 260, 10));

        jtxLocalidadEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxLocalidadEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxLocalidadEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxLocalidadEmpresa.setBorder(null);
        jpEmpresa.add(jtxLocalidadEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 260, 240, 30));

        jlbLocalidadEmpresa.setDisplayedMnemonic('b');
        jlbLocalidadEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbLocalidadEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLocalidadEmpresa.setText("Localidad:");
        jlbLocalidadEmpresa.setToolTipText("");
        jlbLocalidadEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbLocalidadEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbLocalidadEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 270, 110, 20));

        jSeparator40.setForeground(new java.awt.Color(0, 0, 0));
        jpEmpresa.add(jSeparator40, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 260, 10));

        jtxNumEmpleadosEmpresa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNumEmpleadosEmpresa.setForeground(new java.awt.Color(102, 102, 102));
        jtxNumEmpleadosEmpresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNumEmpleadosEmpresa.setBorder(null);
        jpEmpresa.add(jtxNumEmpleadosEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 240, 30));

        jlbNumEmpleadosEmpresa.setDisplayedMnemonic('b');
        jlbNumEmpleadosEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbNumEmpleadosEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNumEmpleadosEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/User Groups_25px_1.png"))); // NOI18N
        jlbNumEmpleadosEmpresa.setText("Número de empleados:");
        jlbNumEmpleadosEmpresa.setToolTipText("");
        jlbNumEmpleadosEmpresa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNumEmpleadosEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpEmpresa.add(jlbNumEmpleadosEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 230, -1));
        jpEmpresa.add(jlbLogoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 370, 240, 230));

        jpPrincipal.add(jpEmpresa, "card3");

        jpProveedor.setBackground(new java.awt.Color(255, 255, 255));
        jpProveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorPedido1.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorPedido1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloProveedor.setDisplayedMnemonic('b');
        jlbTituloProveedor.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloProveedor.setText("Gestor de proveedores");
        jlbTituloProveedor.setToolTipText("");
        jlbTituloProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarProveedor.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarProveedor.setBorder(null);
        jtxBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxBuscarProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxBuscarProveedorKeyTyped(evt);
            }
        });

        jSeparator43.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarProveedor.setBorderPainted(false);
        jbnCerrarProveedor.setContentAreaFilled(false);
        jbnCerrarProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarProveedor.setSelected(true);
        jbnCerrarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarProveedorActionPerformed(evt);
            }
        });

        jbnRegresarProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarProveedor.setBorderPainted(false);
        jbnRegresarProveedor.setContentAreaFilled(false);
        jbnRegresarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarProveedor.setSelected(true);
        jbnRegresarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarProveedorActionPerformed(evt);
            }
        });

        jlbBuscarProveedor.setDisplayedMnemonic('b');
        jlbBuscarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarProveedor.setText("Buscar  ");
        jlbBuscarProveedor.setToolTipText("");
        jlbBuscarProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorPedido1Layout = new javax.swing.GroupLayout(jpBarraSuperiorPedido1);
        jpBarraSuperiorPedido1.setLayout(jpBarraSuperiorPedido1Layout);
        jpBarraSuperiorPedido1Layout.setHorizontalGroup(
            jpBarraSuperiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jlbBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator43)
                    .addComponent(jtxBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addComponent(jbnCerrarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpBarraSuperiorPedido1Layout.setVerticalGroup(
            jpBarraSuperiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido1Layout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarProveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorPedido1Layout.createSequentialGroup()
                        .addComponent(jtxBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator43)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorPedido1Layout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloProveedor)
                    .addComponent(jlbBuscarProveedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorPedido1Layout.createSequentialGroup()
                .addComponent(jbnCerrarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpProveedor.add(jpBarraSuperiorPedido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpBarraInferiorPedido1.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorPedido1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbnActualizarProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnActualizarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnActualizarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jbnActualizarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_1.png"))); // NOI18N
        jbnActualizarProveedor.setText("Actualizar proveedor");
        jbnActualizarProveedor.setBorderPainted(false);
        jbnActualizarProveedor.setContentAreaFilled(false);
        jbnActualizarProveedor.setEnabled(false);
        jbnActualizarProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_2.png"))); // NOI18N
        jbnActualizarProveedor.setSelected(true);
        jbnActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnActualizarProveedorActionPerformed(evt);
            }
        });

        jbnAgregarProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarProveedor.setText("Agregar proveedor");
        jbnAgregarProveedor.setBorderPainted(false);
        jbnAgregarProveedor.setContentAreaFilled(false);
        jbnAgregarProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarProveedor.setSelected(true);
        jbnAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarProveedorActionPerformed(evt);
            }
        });

        jbnEliminarProveedor.setBackground(new java.awt.Color(145, 36, 36));
        jbnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnEliminarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px.png"))); // NOI18N
        jbnEliminarProveedor.setText("Eliminar");
        jbnEliminarProveedor.setBorderPainted(false);
        jbnEliminarProveedor.setContentAreaFilled(false);
        jbnEliminarProveedor.setEnabled(false);
        jbnEliminarProveedor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px_1.png"))); // NOI18N
        jbnEliminarProveedor.setSelected(true);
        jbnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBarraInferiorPedido1Layout = new javax.swing.GroupLayout(jpBarraInferiorPedido1);
        jpBarraInferiorPedido1.setLayout(jpBarraInferiorPedido1Layout);
        jpBarraInferiorPedido1Layout.setHorizontalGroup(
            jpBarraInferiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorPedido1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbnAgregarProveedor)
                .addGap(112, 112, 112)
                .addComponent(jbnEliminarProveedor)
                .addGap(125, 125, 125)
                .addComponent(jbnActualizarProveedor)
                .addGap(150, 150, 150))
        );
        jpBarraInferiorPedido1Layout.setVerticalGroup(
            jpBarraInferiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorPedido1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBarraInferiorPedido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnActualizarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpProveedor.add(jpBarraInferiorPedido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbNombreProveedor.setDisplayedMnemonic('b');
        jlbNombreProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Business Building_25px_5.png"))); // NOI18N
        jlbNombreProveedor.setText("Nombre:");
        jlbNombreProveedor.setToolTipText("");
        jlbNombreProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 130, -1));

        jSeparator41.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator41, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 460, 10));

        jtxNombreProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxNombreProveedor.setForeground(new java.awt.Color(102, 102, 102));
        jtxNombreProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxNombreProveedor.setBorder(null);
        jpProveedor.add(jtxNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 450, 30));

        jSeparator44.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator44, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 550, 10));

        jtxDomicilioProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxDomicilioProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxDomicilioProveedor.setBorder(null);
        jpProveedor.add(jtxDomicilioProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, 540, 30));

        jlbDomicilioProveedor.setDisplayedMnemonic('d');
        jlbDomicilioProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbDomicilioProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbDomicilioProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbDomicilioProveedor.setText("Domicilio:");
        jlbDomicilioProveedor.setToolTipText("");
        jlbDomicilioProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbDomicilioProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbDomicilioProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 120, 20));

        jSeparator45.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator45, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 260, 10));

        jtxTelefonoProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxTelefonoProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxTelefonoProveedor.setBorder(null);
        jpProveedor.add(jtxTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 240, 30));

        jlbTelefonoProveedor.setDisplayedMnemonic('b');
        jlbTelefonoProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbTelefonoProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoProveedor.setText("Telefono:");
        jlbTelefonoProveedor.setToolTipText("");
        jlbTelefonoProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, 120, 30));

        jSeparator46.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator46, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 320, 260, 10));

        jtxRfcProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxRfcProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxRfcProveedor.setBorder(null);
        jpProveedor.add(jtxRfcProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 290, 240, 30));

        jlbRfcProveedor.setDisplayedMnemonic('b');
        jlbRfcProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbRfcProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbRfcProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Postcard With Barcode_25px_1.png"))); // NOI18N
        jlbRfcProveedor.setText("RFC:");
        jlbRfcProveedor.setToolTipText("");
        jlbRfcProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbRfcProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbRfcProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 70, 20));

        jSeparator47.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator47, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 260, 10));

        jtxEmailProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxEmailProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxEmailProveedor.setBorder(null);
        jpProveedor.add(jtxEmailProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, 240, 30));

        jlbEmailProveedor.setDisplayedMnemonic('b');
        jlbEmailProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbEmailProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbEmailProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mail_25px.png"))); // NOI18N
        jlbEmailProveedor.setText("Email:");
        jlbEmailProveedor.setToolTipText("");
        jlbEmailProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbEmailProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbEmailProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 100, 20));

        jlbLogoEmpresaProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLogoEmpresaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N
        jpProveedor.add(jlbLogoEmpresaProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 60, 250, 230));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jlbIconoProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIconoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedor_128.png"))); // NOI18N

        jlbLogoProgramaProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLogoProgramaProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(jlbIconoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbLogoProgramaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jlbLogoProgramaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(jlbIconoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jpProveedor.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 270, 550));

        jlbIdProveedor.setDisplayedMnemonic('b');
        jlbIdProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbIdProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIdProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ID Card_25px_2.png"))); // NOI18N
        jlbIdProveedor.setText("ID proveedor:");
        jlbIdProveedor.setToolTipText("");
        jlbIdProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbIdProveedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpProveedor.add(jlbIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 140, -1));

        jSeparator48.setForeground(new java.awt.Color(0, 0, 0));
        jpProveedor.add(jSeparator48, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 120, 10));

        jtxIdProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxIdProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxIdProveedor.setBorder(null);
        jpProveedor.add(jtxIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 120, 20));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane7.setBorder(null);
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbProveedor.setAutoCreateRowSorter(true);
        jtbProveedor.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbProveedor.setModel(modeloProveedor);
        jtbProveedor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbProveedor.setGridColor(new java.awt.Color(255, 255, 255));
        jtbProveedor.setRowHeight(25);
        jtbProveedor.setRowMargin(8);
        jtbProveedor.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane7.setViewportView(jtbProveedor);

        jpProveedor.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 860, 240));

        jpPrincipal.add(jpProveedor, "card3");

        jpOpcionesyAsistencia.setBackground(new java.awt.Color(255, 255, 255));
        jpOpcionesyAsistencia.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpOpcionesContenido.setBackground(new java.awt.Color(204, 204, 204));
        jpOpcionesContenido.setLayout(new java.awt.CardLayout());

        jpOpcion1.setBackground(new java.awt.Color(255, 255, 255));

        jbnCerrarOpciones1.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarOpciones1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px.png"))); // NOI18N
        jbnCerrarOpciones1.setBorderPainted(false);
        jbnCerrarOpciones1.setContentAreaFilled(false);
        jbnCerrarOpciones1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarOpciones1.setSelected(true);
        jbnCerrarOpciones1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarOpciones1ActionPerformed(evt);
            }
        });

        jpPanelInformacion.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jpPanelInformacionLayout = new javax.swing.GroupLayout(jpPanelInformacion);
        jpPanelInformacion.setLayout(jpPanelInformacionLayout);
        jpPanelInformacionLayout.setHorizontalGroup(
            jpPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 787, Short.MAX_VALUE)
        );
        jpPanelInformacionLayout.setVerticalGroup(
            jpPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoTriton.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N

        javax.swing.GroupLayout jpOpcion1Layout = new javax.swing.GroupLayout(jpOpcion1);
        jpOpcion1.setLayout(jpOpcion1Layout);
        jpOpcion1Layout.setHorizontalGroup(
            jpOpcion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpOpcion1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jpPanelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
            .addGroup(jpOpcion1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpOpcion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnCerrarOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpOpcion1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(194, 194, 194))))
        );
        jpOpcion1Layout.setVerticalGroup(
            jpOpcion1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOpcion1Layout.createSequentialGroup()
                .addComponent(jbnCerrarOpciones1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpPanelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcion1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpOpcionesContenido.add(jpOpcion1, "card4");

        jpOpcionesPoliticas.setBackground(new java.awt.Color(102, 102, 102));

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbnCerrarOpciones.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarOpciones.setBorderPainted(false);
        jbnCerrarOpciones.setContentAreaFilled(false);
        jbnCerrarOpciones.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarOpciones.setSelected(true);
        jbnCerrarOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarOpcionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpOpcionesPoliticasLayout = new javax.swing.GroupLayout(jpOpcionesPoliticas);
        jpOpcionesPoliticas.setLayout(jpOpcionesPoliticasLayout);
        jpOpcionesPoliticasLayout.setHorizontalGroup(
            jpOpcionesPoliticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcionesPoliticasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbnCerrarOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcionesPoliticasLayout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jpOpcionesPoliticasLayout.setVerticalGroup(
            jpOpcionesPoliticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOpcionesPoliticasLayout.createSequentialGroup()
                .addComponent(jbnCerrarOpciones)
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jpOpcionesContenido.add(jpOpcionesPoliticas, "card2");

        jpOpcionDescuento.setBackground(new java.awt.Color(102, 102, 102));

        jbnCerrarOpciones2.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarOpciones2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarOpciones2.setBorderPainted(false);
        jbnCerrarOpciones2.setContentAreaFilled(false);
        jbnCerrarOpciones2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarOpciones2.setSelected(true);
        jbnCerrarOpciones2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarOpciones2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpOpcionDescuentoLayout = new javax.swing.GroupLayout(jpOpcionDescuento);
        jpOpcionDescuento.setLayout(jpOpcionDescuentoLayout);
        jpOpcionDescuentoLayout.setHorizontalGroup(
            jpOpcionDescuentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpOpcionDescuentoLayout.createSequentialGroup()
                .addGap(0, 834, Short.MAX_VALUE)
                .addComponent(jbnCerrarOpciones2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpOpcionDescuentoLayout.setVerticalGroup(
            jpOpcionDescuentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOpcionDescuentoLayout.createSequentialGroup()
                .addComponent(jbnCerrarOpciones2)
                .addGap(0, 611, Short.MAX_VALUE))
        );

        jpOpcionesContenido.add(jpOpcionDescuento, "card3");

        jpOpcionesyAsistencia.add(jpOpcionesContenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 890, 670));

        jpOpcionesMenu.setBackground(new java.awt.Color(22, 114, 185));
        jpOpcionesMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlbLogoProgramaProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLogoProgramaProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N
        jpOpcionesMenu.add(jlbLogoProgramaProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 285, 185));

        jpInformacionAsistencia.setBackground(new java.awt.Color(51, 153, 255));
        jpInformacionAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpInformacionAsistenciaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpInformacionAsistenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpInformacionAsistenciaMouseExited(evt);
            }
        });

        jlbOpcionNombreRedes1.setBackground(new java.awt.Color(0, 51, 255));
        jlbOpcionNombreRedes1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbOpcionNombreRedes1.setForeground(new java.awt.Color(255, 255, 255));
        jlbOpcionNombreRedes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbOpcionNombreRedes1.setText("Información del software");

        javax.swing.GroupLayout jpInformacionAsistenciaLayout = new javax.swing.GroupLayout(jpInformacionAsistencia);
        jpInformacionAsistencia.setLayout(jpInformacionAsistenciaLayout);
        jpInformacionAsistenciaLayout.setHorizontalGroup(
            jpInformacionAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInformacionAsistenciaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jlbOpcionNombreRedes1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jpInformacionAsistenciaLayout.setVerticalGroup(
            jpInformacionAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInformacionAsistenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbOpcionNombreRedes1, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpOpcionesMenu.add(jpInformacionAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 310, 40));

        jpPoliticasAsistencia.setBackground(new java.awt.Color(51, 153, 255));
        jpPoliticasAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpPoliticasAsistenciaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jpPoliticasAsistenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpPoliticasAsistenciaMouseExited(evt);
            }
        });

        jlbOpcionNombreRedes2.setBackground(new java.awt.Color(51, 102, 255));
        jlbOpcionNombreRedes2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlbOpcionNombreRedes2.setForeground(new java.awt.Color(255, 255, 255));
        jlbOpcionNombreRedes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbOpcionNombreRedes2.setText("Politicas de descuento");

        javax.swing.GroupLayout jpPoliticasAsistenciaLayout = new javax.swing.GroupLayout(jpPoliticasAsistencia);
        jpPoliticasAsistencia.setLayout(jpPoliticasAsistenciaLayout);
        jpPoliticasAsistenciaLayout.setHorizontalGroup(
            jpPoliticasAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPoliticasAsistenciaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jlbOpcionNombreRedes2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jpPoliticasAsistenciaLayout.setVerticalGroup(
            jpPoliticasAsistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPoliticasAsistenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbOpcionNombreRedes2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpOpcionesMenu.add(jpPoliticasAsistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 310, 50));

        jbnRegresarOpciones.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarOpciones.setBorderPainted(false);
        jbnRegresarOpciones.setContentAreaFilled(false);
        jbnRegresarOpciones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarOpciones.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarOpciones.setSelected(true);
        jbnRegresarOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarOpcionesActionPerformed(evt);
            }
        });
        jpOpcionesMenu.add(jbnRegresarOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jlbIconoOpciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIconoOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tool_150.png"))); // NOI18N
        jpOpcionesMenu.add(jlbIconoOpciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 480, 190, 150));

        jpOpcionesyAsistencia.add(jpOpcionesMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 670));

        jpPrincipal.add(jpOpcionesyAsistencia, "card2");

        jpProducto.setBackground(new java.awt.Color(255, 255, 255));
        jpProducto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraSuperiorProducto.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloProducto.setDisplayedMnemonic('b');
        jlbTituloProducto.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloProducto.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloProducto.setText("Gestor de producto");
        jlbTituloProducto.setToolTipText("");
        jlbTituloProducto.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarProducto.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarProducto.setBorder(null);

        jSeparator49.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarProducto.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarProducto.setBorderPainted(false);
        jbnCerrarProducto.setContentAreaFilled(false);
        jbnCerrarProducto.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarProducto.setSelected(true);
        jbnCerrarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarProductoActionPerformed(evt);
            }
        });

        jbnRegresarProducto.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarProducto.setBorderPainted(false);
        jbnRegresarProducto.setContentAreaFilled(false);
        jbnRegresarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarProducto.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarProducto.setSelected(true);
        jbnRegresarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarProductoActionPerformed(evt);
            }
        });

        jlbBuscarProducto.setDisplayedMnemonic('b');
        jlbBuscarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarProducto.setText("Buscar  ");
        jlbBuscarProducto.setToolTipText("");
        jlbBuscarProducto.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorProductoLayout = new javax.swing.GroupLayout(jpBarraSuperiorProducto);
        jpBarraSuperiorProducto.setLayout(jpBarraSuperiorProductoLayout);
        jpBarraSuperiorProductoLayout.setHorizontalGroup(
            jpBarraSuperiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorProductoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jlbBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator49)
                    .addComponent(jtxBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addComponent(jbnCerrarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorProductoLayout.setVerticalGroup(
            jpBarraSuperiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorProductoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarProducto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorProductoLayout.createSequentialGroup()
                        .addComponent(jtxBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator49)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorProductoLayout.createSequentialGroup()
                .addGroup(jpBarraSuperiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloProducto)
                    .addComponent(jlbBuscarProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorProductoLayout.createSequentialGroup()
                .addComponent(jbnCerrarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpProducto.add(jpBarraSuperiorProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpBarraInferiorProducto.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jbnActualizarProducto.setBackground(new java.awt.Color(145, 36, 36));
        jbnActualizarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnActualizarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jbnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_1.png"))); // NOI18N
        jbnActualizarProducto.setText("Actualizar producto");
        jbnActualizarProducto.setBorderPainted(false);
        jbnActualizarProducto.setContentAreaFilled(false);
        jbnActualizarProducto.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sync_48px_2.png"))); // NOI18N
        jbnActualizarProducto.setSelected(true);
        jbnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnActualizarProductoActionPerformed(evt);
            }
        });

        jbnAgregarProveedor1.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarProveedor1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnAgregarProveedor1.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px.png"))); // NOI18N
        jbnAgregarProveedor1.setText("Agregar producto");
        jbnAgregarProveedor1.setBorderPainted(false);
        jbnAgregarProveedor1.setContentAreaFilled(false);
        jbnAgregarProveedor1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Plus_48px_1.png"))); // NOI18N
        jbnAgregarProveedor1.setSelected(true);
        jbnAgregarProveedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarProveedor1ActionPerformed(evt);
            }
        });

        jbnEliminarProducto.setBackground(new java.awt.Color(145, 36, 36));
        jbnEliminarProducto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnEliminarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px.png"))); // NOI18N
        jbnEliminarProducto.setText("Eliminar");
        jbnEliminarProducto.setBorderPainted(false);
        jbnEliminarProducto.setContentAreaFilled(false);
        jbnEliminarProducto.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Minus_48px_1.png"))); // NOI18N
        jbnEliminarProducto.setSelected(true);
        jbnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBarraInferiorProductoLayout = new javax.swing.GroupLayout(jpBarraInferiorProducto);
        jpBarraInferiorProducto.setLayout(jpBarraInferiorProductoLayout);
        jpBarraInferiorProductoLayout.setHorizontalGroup(
            jpBarraInferiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbnAgregarProveedor1)
                .addGap(112, 112, 112)
                .addComponent(jbnEliminarProducto)
                .addGap(125, 125, 125)
                .addComponent(jbnActualizarProducto)
                .addGap(150, 150, 150))
        );
        jpBarraInferiorProductoLayout.setVerticalGroup(
            jpBarraInferiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorProductoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpBarraInferiorProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnActualizarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbnAgregarProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jpProducto.add(jpBarraInferiorProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jlbLogoEmpresaProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLogoEmpresaProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N
        jpProducto.add(jlbLogoEmpresaProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 80, 80, 60));

        jpContenidoLogoProducto.setBackground(new java.awt.Color(204, 204, 204));

        jlbIconoProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbIconoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/71xN3Ijq4DL._150_.png"))); // NOI18N

        jlbLogoProgramaProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbLogoProgramaProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Categorias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), javax.swing.UIManager.getDefaults().getColor("Button.darcula.selection.color1"))); // NOI18N

        jLabel7.setText("Arpones");

        jLabel8.setText("Redes");

        jLabel9.setText("Anzuelos");

        jLabel10.setText("Canas ");

        jLabel11.setText("misc");

        jLabel12.setText("todas");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(60, 60, 60))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpContenidoLogoProductoLayout = new javax.swing.GroupLayout(jpContenidoLogoProducto);
        jpContenidoLogoProducto.setLayout(jpContenidoLogoProductoLayout);
        jpContenidoLogoProductoLayout.setHorizontalGroup(
            jpContenidoLogoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContenidoLogoProductoLayout.createSequentialGroup()
                .addGroup(jpContenidoLogoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContenidoLogoProductoLayout.createSequentialGroup()
                        .addGroup(jpContenidoLogoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpContenidoLogoProductoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jlbLogoProgramaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpContenidoLogoProductoLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jlbIconoProducto)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpContenidoLogoProductoLayout.setVerticalGroup(
            jpContenidoLogoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContenidoLogoProductoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jlbLogoProgramaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jlbIconoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpProducto.add(jpContenidoLogoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 270, 550));

        jpListaProd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpListaProd.setAutoscrolls(true);
        jpListaProd.setName(""); // NOI18N

        javax.swing.GroupLayout jpListaProdLayout = new javax.swing.GroupLayout(jpListaProd);
        jpListaProd.setLayout(jpListaProdLayout);
        jpListaProdLayout.setHorizontalGroup(
            jpListaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );
        jpListaProdLayout.setVerticalGroup(
            jpListaProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );

        jpProducto.add(jpListaProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 770, 470));

        jpPrincipal.add(jpProducto, "card3");

        jpVenta.setBackground(new java.awt.Color(255, 255, 255));
        jpVenta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpPanelSuperiorVenta.setBackground(new java.awt.Color(22, 114, 185));

        jtxBuscarProductoVenta.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarProductoVenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarProductoVenta.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarProductoVenta.setBorder(null);
        jtxBuscarProductoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxBuscarProductoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxBuscarProductoVentaKeyTyped(evt);
            }
        });

        jSeparator50.setForeground(new java.awt.Color(255, 255, 255));

        jlbBuscarPedido2.setDisplayedMnemonic('b');
        jlbBuscarPedido2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarPedido2.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarPedido2.setText("Buscar  ");
        jlbBuscarPedido2.setToolTipText("");
        jlbBuscarPedido2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarPedido2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jbnCerrarVenta.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarVenta.setBorderPainted(false);
        jbnCerrarVenta.setContentAreaFilled(false);
        jbnCerrarVenta.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarVenta.setSelected(true);
        jbnCerrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarVentaActionPerformed(evt);
            }
        });

        jbnEliminarProducto2.setBackground(new java.awt.Color(145, 36, 36));
        jbnEliminarProducto2.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jbnEliminarProducto2.setForeground(new java.awt.Color(255, 255, 255));
        jbnEliminarProducto2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Shopping Cart_50px.png"))); // NOI18N
        jbnEliminarProducto2.setText("0");
        jbnEliminarProducto2.setBorderPainted(false);
        jbnEliminarProducto2.setContentAreaFilled(false);
        jbnEliminarProducto2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbnEliminarProducto2.setSelected(true);
        jbnEliminarProducto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnEliminarProducto2ActionPerformed(evt);
            }
        });

        jbnRegresarEmpleado1.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarEmpleado1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jbnRegresarEmpleado1.setForeground(new java.awt.Color(255, 255, 255));
        jbnRegresarEmpleado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarEmpleado1.setText("Regresar a menu");
        jbnRegresarEmpleado1.setBorderPainted(false);
        jbnRegresarEmpleado1.setContentAreaFilled(false);
        jbnRegresarEmpleado1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarEmpleado1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarEmpleado1.setSelected(true);
        jbnRegresarEmpleado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarEmpleado1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPanelSuperiorVentaLayout = new javax.swing.GroupLayout(jpPanelSuperiorVenta);
        jpPanelSuperiorVenta.setLayout(jpPanelSuperiorVentaLayout);
        jpPanelSuperiorVentaLayout.setHorizontalGroup(
            jpPanelSuperiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelSuperiorVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbBuscarPedido2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jpPanelSuperiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxBuscarProductoVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(jSeparator50))
                .addGap(67, 67, 67)
                .addComponent(jbnRegresarEmpleado1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addComponent(jbnEliminarProducto2)
                .addGap(139, 139, 139)
                .addComponent(jbnCerrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpPanelSuperiorVentaLayout.setVerticalGroup(
            jpPanelSuperiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelSuperiorVentaLayout.createSequentialGroup()
                .addComponent(jbnCerrarVenta)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jpPanelSuperiorVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPanelSuperiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPanelSuperiorVentaLayout.createSequentialGroup()
                        .addComponent(jtxBuscarProductoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator50, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbBuscarPedido2)
                    .addComponent(jbnRegresarEmpleado1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbnEliminarProducto2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpVenta.add(jpPanelSuperiorVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 70));

        jScrollPane14.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane14.setBorder(null);
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbProductoVenta.setAutoCreateRowSorter(true);
        jtbProductoVenta.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbProductoVenta.setModel(modeloProductoVenta);
        jtbProductoVenta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbProductoVenta.setGridColor(new java.awt.Color(255, 255, 255));
        jtbProductoVenta.setRowHeight(25);
        jtbProductoVenta.setRowMargin(8);
        jtbProductoVenta.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane14.setViewportView(jtbProductoVenta);

        jpVenta.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 640, 159));

        jScrollPane15.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane15.setBorder(null);
        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbDetalleVenta.setAutoCreateRowSorter(true);
        jtbDetalleVenta.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbDetalleVenta.setModel(modeloDetalleVenta);
        jtbDetalleVenta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbDetalleVenta.setGridColor(new java.awt.Color(255, 255, 255));
        jtbDetalleVenta.setRowHeight(25);
        jtbDetalleVenta.setRowMargin(8);
        jtbDetalleVenta.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane15.setViewportView(jtbDetalleVenta);

        jpVenta.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 630, 210));

        jlbBuscarPedido4.setBackground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido4.setDisplayedMnemonic('b');
        jlbBuscarPedido4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlbBuscarPedido4.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido4.setText("Productos");
        jlbBuscarPedido4.setToolTipText("");
        jlbBuscarPedido4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarPedido4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jpVenta.add(jlbBuscarPedido4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 120, 35));

        jcbxClienteVenta.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jcbxClienteVenta.setModel(comboClienteVenta);
        jcbxClienteVenta.setBorder(null);
        jcbxClienteVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcbxClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxClienteVentaActionPerformed(evt);
            }
        });
        jpVenta.add(jcbxClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 80, 370, 30));

        jlbClienteVenta.setDisplayedMnemonic('b');
        jlbClienteVenta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbClienteVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbClienteVenta.setText("Cliente:");
        jlbClienteVenta.setToolTipText("");
        jlbClienteVenta.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbClienteVenta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpVenta.add(jlbClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 120, 30));

        jtxTotalVenta.setEditable(false);
        jtxTotalVenta.setBackground(new java.awt.Color(204, 204, 204));
        jtxTotalVenta.setFont(new java.awt.Font("Century Gothic", 1, 32)); // NOI18N
        jtxTotalVenta.setBorder(null);
        jpVenta.add(jtxTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 430, 170, 40));

        jlbIva.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jlbIva.setText("Iva:");
        jpVenta.add(jlbIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 390, 50, -1));

        jSeparator54.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator54.setForeground(new java.awt.Color(204, 204, 204));
        jpVenta.add(jSeparator54, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 270, 180, 10));

        jlbSignoTotal.setBackground(new java.awt.Color(204, 204, 204));
        jlbSignoTotal.setFont(new java.awt.Font("Century Gothic", 1, 32)); // NOI18N
        jlbSignoTotal.setText("$");
        jpVenta.add(jlbSignoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 430, -1, -1));

        jlbTotalVenta1.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jlbTotalVenta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Receipt_50px_3.png"))); // NOI18N
        jlbTotalVenta1.setText("Total: ");
        jpVenta.add(jlbTotalVenta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 430, 120, -1));

        jlbCambio.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jlbCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Coins_50px.png"))); // NOI18N
        jlbCambio.setText("Cambio:");
        jpVenta.add(jlbCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 230, 160, -1));

        jtxIva.setEditable(false);
        jtxIva.setBackground(new java.awt.Color(204, 204, 204));
        jtxIva.setFont(new java.awt.Font("Century Gothic", 1, 32)); // NOI18N
        jtxIva.setBorder(null);
        jpVenta.add(jtxIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 390, 120, 40));

        jtxSubtotalVenta.setEditable(false);
        jtxSubtotalVenta.setBackground(new java.awt.Color(204, 204, 204));
        jtxSubtotalVenta.setFont(new java.awt.Font("Century Gothic", 1, 32)); // NOI18N
        jtxSubtotalVenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jtxSubtotalVenta.setBorder(null);
        jpVenta.add(jtxSubtotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 350, 180, 40));

        jlbTotalVenta3.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jlbTotalVenta3.setText("SubTotal:");
        jpVenta.add(jlbTotalVenta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 340, 90, -1));

        jtxCambio.setEditable(false);
        jtxCambio.setBackground(new java.awt.Color(204, 204, 204));
        jtxCambio.setFont(new java.awt.Font("Century Gothic", 0, 28)); // NOI18N
        jtxCambio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxCambio.setBorder(null);
        jpVenta.add(jtxCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 230, 180, 40));

        jSeparator55.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator55.setForeground(new java.awt.Color(204, 204, 204));
        jpVenta.add(jSeparator55, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 470, 200, 10));

        jlbPago.setDisplayedMnemonic('p');
        jlbPago.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jlbPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Paper Money_50px_2.png"))); // NOI18N
        jlbPago.setText("Pago: ");
        jpVenta.add(jlbPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 150, 150, -1));

        jSeparator61.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator61.setForeground(new java.awt.Color(204, 204, 204));
        jpVenta.add(jSeparator61, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 200, 190, 10));

        jtxPagoPor1.setFont(new java.awt.Font("Century Gothic", 0, 28)); // NOI18N
        jtxPagoPor1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxPagoPor1.setBorder(null);
        jtxPagoPor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxPagoPor1ActionPerformed(evt);
            }
        });
        jtxPagoPor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxPagoPor1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxPagoPor1KeyTyped(evt);
            }
        });
        jpVenta.add(jtxPagoPor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 190, -1));

        jpPanelInferiorVenta.setBackground(new java.awt.Color(22, 114, 185));

        jbnAgregarProveedor2.setBackground(new java.awt.Color(145, 36, 36));
        jbnAgregarProveedor2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jbnAgregarProveedor2.setForeground(new java.awt.Color(255, 255, 255));
        jbnAgregarProveedor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Ok_48px.png"))); // NOI18N
        jbnAgregarProveedor2.setText("Finalizar Venta");
        jbnAgregarProveedor2.setBorderPainted(false);
        jbnAgregarProveedor2.setContentAreaFilled(false);
        jbnAgregarProveedor2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Ok_48px_4.png"))); // NOI18N
        jbnAgregarProveedor2.setSelected(true);
        jbnAgregarProveedor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAgregarProveedor2ActionPerformed(evt);
            }
        });

        jbnCerrarSesionVenta.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarSesionVenta.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jbnCerrarSesionVenta.setForeground(new java.awt.Color(255, 255, 255));
        jbnCerrarSesionVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Door Opened_50px.png"))); // NOI18N
        jbnCerrarSesionVenta.setText("Cerrar sesión");
        jbnCerrarSesionVenta.setBorderPainted(false);
        jbnCerrarSesionVenta.setContentAreaFilled(false);
        jbnCerrarSesionVenta.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Door Closed_50px_1.png"))); // NOI18N
        jbnCerrarSesionVenta.setSelected(true);
        jbnCerrarSesionVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarSesionVentaActionPerformed(evt);
            }
        });

        jbnCancelarVenta.setBackground(new java.awt.Color(145, 36, 36));
        jbnCancelarVenta.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jbnCancelarVenta.setForeground(new java.awt.Color(255, 255, 255));
        jbnCancelarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCancelarVenta.setText("Cancelar Venta");
        jbnCancelarVenta.setBorderPainted(false);
        jbnCancelarVenta.setContentAreaFilled(false);
        jbnCancelarVenta.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCancelarVenta.setSelected(true);
        jbnCancelarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCancelarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPanelInferiorVentaLayout = new javax.swing.GroupLayout(jpPanelInferiorVenta);
        jpPanelInferiorVenta.setLayout(jpPanelInferiorVentaLayout);
        jpPanelInferiorVentaLayout.setHorizontalGroup(
            jpPanelInferiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPanelInferiorVentaLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jbnCerrarSesionVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 394, Short.MAX_VALUE)
                .addComponent(jbnCancelarVenta)
                .addGap(63, 63, 63)
                .addComponent(jbnAgregarProveedor2)
                .addGap(61, 61, 61))
        );
        jpPanelInferiorVentaLayout.setVerticalGroup(
            jpPanelInferiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelInferiorVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPanelInferiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbnCerrarSesionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpPanelInferiorVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbnCancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jbnAgregarProveedor2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jpVenta.add(jpPanelInferiorVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1200, 70));

        jpPanelContenedor.setBackground(new java.awt.Color(153, 153, 153));
        jpPanelContenedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbBuscarPedido3.setBackground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido3.setDisplayedMnemonic('b');
        jlbBuscarPedido3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jlbBuscarPedido3.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido3.setText("Detalle Venta");
        jlbBuscarPedido3.setToolTipText("");
        jlbBuscarPedido3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarPedido3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpPanelContenedorLayout = new javax.swing.GroupLayout(jpPanelContenedor);
        jpPanelContenedor.setLayout(jpPanelContenedorLayout);
        jpPanelContenedorLayout.setHorizontalGroup(
            jpPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbBuscarPedido3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(489, Short.MAX_VALUE))
        );
        jpPanelContenedorLayout.setVerticalGroup(
            jpPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPanelContenedorLayout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(jlbBuscarPedido3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
        );

        jpVenta.add(jpPanelContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 660, 530));

        jpPrincipal.add(jpVenta, "card11");

        javax.swing.GroupLayout jpReportesLayout = new javax.swing.GroupLayout(jpReportes);
        jpReportes.setLayout(jpReportesLayout);
        jpReportesLayout.setHorizontalGroup(
            jpReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        jpReportesLayout.setVerticalGroup(
            jpReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        jpPrincipal.add(jpReportes, "card11");

        javax.swing.GroupLayout jpCompraLayout = new javax.swing.GroupLayout(jpCompra);
        jpCompra.setLayout(jpCompraLayout);
        jpCompraLayout.setHorizontalGroup(
            jpCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        jpCompraLayout.setVerticalGroup(
            jpCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        jpPrincipal.add(jpCompra, "card12");

        jPedido1.setBackground(new java.awt.Color(102, 102, 102));
        jPedido1.setDoubleBuffered(false);
        jPedido1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jScrollPane13.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane13.setBorder(null);
        jScrollPane13.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbPedidogenral.setAutoCreateRowSorter(true);
        jtbPedidogenral.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbPedidogenral.setModel(modeloPedido);
        jtbPedidogenral.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbPedidogenral.setGridColor(new java.awt.Color(255, 255, 255));
        jtbPedidogenral.setRowHeight(25);
        jtbPedidogenral.setRowMargin(8);
        jtbPedidogenral.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane13.setViewportView(jtbPedidogenral);

        javax.swing.GroupLayout jpGeneralLayout = new javax.swing.GroupLayout(jpGeneral);
        jpGeneral.setLayout(jpGeneralLayout);
        jpGeneralLayout.setHorizontalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpGeneralLayout.setVerticalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGeneralLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("General", jpGeneral);

        jScrollPane9.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane9.setBorder(null);
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbPedidoRecibido.setAutoCreateRowSorter(true);
        jtbPedidoRecibido.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbPedidoRecibido.setModel(modeloPedido);
        jtbPedidoRecibido.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbPedidoRecibido.setGridColor(new java.awt.Color(255, 255, 255));
        jtbPedidoRecibido.setRowHeight(25);
        jtbPedidoRecibido.setRowMargin(8);
        jtbPedidoRecibido.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane9.setViewportView(jtbPedidoRecibido);

        javax.swing.GroupLayout jpPestañaRecibidoLayout = new javax.swing.GroupLayout(jpPestañaRecibido);
        jpPestañaRecibido.setLayout(jpPestañaRecibidoLayout);
        jpPestañaRecibidoLayout.setHorizontalGroup(
            jpPestañaRecibidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPestañaRecibidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpPestañaRecibidoLayout.setVerticalGroup(
            jpPestañaRecibidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPestañaRecibidoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Recibido", jpPestañaRecibido);

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane11.setBorder(null);
        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbTransferido.setAutoCreateRowSorter(true);
        jtbTransferido.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbTransferido.setModel(modeloProveedor);
        jtbTransferido.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbTransferido.setGridColor(new java.awt.Color(255, 255, 255));
        jtbTransferido.setRowHeight(25);
        jtbTransferido.setRowMargin(8);
        jtbTransferido.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane11.setViewportView(jtbTransferido);

        javax.swing.GroupLayout jpPestañaTransferidoLayout = new javax.swing.GroupLayout(jpPestañaTransferido);
        jpPestañaTransferido.setLayout(jpPestañaTransferidoLayout);
        jpPestañaTransferidoLayout.setHorizontalGroup(
            jpPestañaTransferidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE)
        );
        jpPestañaTransferidoLayout.setVerticalGroup(
            jpPestañaTransferidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPestañaTransferidoLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Transeferido", jpPestañaTransferido);

        jScrollPane10.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane10.setBorder(null);
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbPedido2.setAutoCreateRowSorter(true);
        jtbPedido2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbPedido2.setModel(modeloProveedor);
        jtbPedido2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbPedido2.setGridColor(new java.awt.Color(255, 255, 255));
        jtbPedido2.setRowHeight(25);
        jtbPedido2.setRowMargin(8);
        jtbPedido2.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane10.setViewportView(jtbPedido2);

        javax.swing.GroupLayout jpPestañaEnviadoLayout = new javax.swing.GroupLayout(jpPestañaEnviado);
        jpPestañaEnviado.setLayout(jpPestañaEnviadoLayout);
        jpPestañaEnviadoLayout.setHorizontalGroup(
            jpPestañaEnviadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 1195, Short.MAX_VALUE)
        );
        jpPestañaEnviadoLayout.setVerticalGroup(
            jpPestañaEnviadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPestañaEnviadoLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Pedido enviado", jpPestañaEnviado);

        Prueba.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Prueba);

        javax.swing.GroupLayout jpPestañaCompletadoLayout = new javax.swing.GroupLayout(jpPestañaCompletado);
        jpPestañaCompletado.setLayout(jpPestañaCompletadoLayout);
        jpPestañaCompletadoLayout.setHorizontalGroup(
            jpPestañaCompletadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPestañaCompletadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpPestañaCompletadoLayout.setVerticalGroup(
            jpPestañaCompletadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPestañaCompletadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedido Completado", jpPestañaCompletado);

        jPedido1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1200, 290));

        jpBarraSuperiorPedido2.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorPedido2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloPedido1.setDisplayedMnemonic('b');
        jlbTituloPedido1.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloPedido1.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloPedido1.setText("Gestor de pedidos");
        jlbTituloPedido1.setToolTipText("");
        jlbTituloPedido1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloPedido1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jtxBuscarPedido1.setBackground(new java.awt.Color(22, 114, 185));
        jtxBuscarPedido1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxBuscarPedido1.setForeground(new java.awt.Color(255, 255, 255));
        jtxBuscarPedido1.setBorder(null);

        jSeparator57.setForeground(new java.awt.Color(255, 255, 255));

        jbnCerrarPedido1.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarPedido1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarPedido1.setBorderPainted(false);
        jbnCerrarPedido1.setContentAreaFilled(false);
        jbnCerrarPedido1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarPedido1.setSelected(true);
        jbnCerrarPedido1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarPedido1ActionPerformed(evt);
            }
        });

        jbnRegresarPedido1.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarPedido1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarPedido1.setBorderPainted(false);
        jbnRegresarPedido1.setContentAreaFilled(false);
        jbnRegresarPedido1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarPedido1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarPedido1.setSelected(true);
        jbnRegresarPedido1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarPedido1ActionPerformed(evt);
            }
        });

        jlbBuscarPedido1.setDisplayedMnemonic('b');
        jlbBuscarPedido1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlbBuscarPedido1.setForeground(new java.awt.Color(255, 255, 255));
        jlbBuscarPedido1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Search_48px.png"))); // NOI18N
        jlbBuscarPedido1.setText("Buscar  ");
        jlbBuscarPedido1.setToolTipText("");
        jlbBuscarPedido1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbBuscarPedido1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorPedido2Layout = new javax.swing.GroupLayout(jpBarraSuperiorPedido2);
        jpBarraSuperiorPedido2.setLayout(jpBarraSuperiorPedido2Layout);
        jpBarraSuperiorPedido2Layout.setHorizontalGroup(
            jpBarraSuperiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jbnRegresarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTituloPedido1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(jlbBuscarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBarraSuperiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator57)
                    .addComponent(jtxBuscarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113)
                .addComponent(jbnCerrarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorPedido2Layout.setVerticalGroup(
            jpBarraSuperiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido2Layout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbnRegresarPedido1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpBarraSuperiorPedido2Layout.createSequentialGroup()
                        .addComponent(jtxBuscarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator57)))
                .addGap(3, 3, 3))
            .addGroup(jpBarraSuperiorPedido2Layout.createSequentialGroup()
                .addGroup(jpBarraSuperiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbTituloPedido1)
                    .addComponent(jlbBuscarPedido1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorPedido2Layout.createSequentialGroup()
                .addComponent(jbnCerrarPedido1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPedido1.add(jpBarraSuperiorPedido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpDescripcionPedido.setBackground(new java.awt.Color(255, 255, 255));
        jpDescripcionPedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sort Down_48px_1.png"))); // NOI18N
        jLabel4.setText("Información ");
        jpDescripcionPedido.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, -1, 25));

        jSeparator58.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator58.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator58.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jpDescripcionPedido.add(jSeparator58, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 42, -1, 230));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Sort Down_48px_1.png"))); // NOI18N
        jLabel5.setText("Descripción");
        jpDescripcionPedido.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 150, 30));

        jSeparator59.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator59.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator59.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator59.setAlignmentX(0.8F);
        jSeparator59.setAlignmentY(0.8F);
        jSeparator59.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jSeparator59.setPreferredSize(new java.awt.Dimension(5, 0));
        jpDescripcionPedido.add(jSeparator59, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 40, -1, 130));

        jPedido1.add(jpDescripcionPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 1150, 280));

        jpPrincipal.add(jPedido1, "card13");

        jpDetallePedido.setBackground(new java.awt.Color(255, 255, 255));
        jpDetallePedido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpBarraInferiorPedido2.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraInferiorPedido2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpBarraInferiorPedido2Layout = new javax.swing.GroupLayout(jpBarraInferiorPedido2);
        jpBarraInferiorPedido2.setLayout(jpBarraInferiorPedido2Layout);
        jpBarraInferiorPedido2Layout.setHorizontalGroup(
            jpBarraInferiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1196, Short.MAX_VALUE)
        );
        jpBarraInferiorPedido2Layout.setVerticalGroup(
            jpBarraInferiorPedido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        jpDetallePedido.add(jpBarraInferiorPedido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 1200, 60));

        jpBarraSuperiorPedido3.setBackground(new java.awt.Color(22, 114, 185));
        jpBarraSuperiorPedido3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlbTituloPedido2.setDisplayedMnemonic('b');
        jlbTituloPedido2.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloPedido2.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloPedido2.setText("Regresar a pedidos");
        jlbTituloPedido2.setToolTipText("");
        jlbTituloPedido2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloPedido2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jbnCerrarPedido2.setBackground(new java.awt.Color(145, 36, 36));
        jbnCerrarPedido2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_2.png"))); // NOI18N
        jbnCerrarPedido2.setBorderPainted(false);
        jbnCerrarPedido2.setContentAreaFilled(false);
        jbnCerrarPedido2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cancel_50px_1.png"))); // NOI18N
        jbnCerrarPedido2.setSelected(true);
        jbnCerrarPedido2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnCerrarPedido2ActionPerformed(evt);
            }
        });

        jbnRegresarPedido2.setBackground(new java.awt.Color(145, 36, 36));
        jbnRegresarPedido2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px.png"))); // NOI18N
        jbnRegresarPedido2.setBorderPainted(false);
        jbnRegresarPedido2.setContentAreaFilled(false);
        jbnRegresarPedido2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbnRegresarPedido2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Back Arrow_48px_1.png"))); // NOI18N
        jbnRegresarPedido2.setSelected(true);
        jbnRegresarPedido2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarPedido2ActionPerformed(evt);
            }
        });

        jlbTituloPedido3.setDisplayedMnemonic('b');
        jlbTituloPedido3.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jlbTituloPedido3.setForeground(new java.awt.Color(255, 255, 255));
        jlbTituloPedido3.setText("Detalle pedido");
        jlbTituloPedido3.setToolTipText("");
        jlbTituloPedido3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTituloPedido3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jpBarraSuperiorPedido3Layout = new javax.swing.GroupLayout(jpBarraSuperiorPedido3);
        jpBarraSuperiorPedido3.setLayout(jpBarraSuperiorPedido3Layout);
        jpBarraSuperiorPedido3Layout.setHorizontalGroup(
            jpBarraSuperiorPedido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbnRegresarPedido2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlbTituloPedido2)
                .addGap(345, 345, 345)
                .addComponent(jlbTituloPedido3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addComponent(jbnCerrarPedido2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBarraSuperiorPedido3Layout.setVerticalGroup(
            jpBarraSuperiorPedido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraSuperiorPedido3Layout.createSequentialGroup()
                .addComponent(jbnRegresarPedido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraSuperiorPedido3Layout.createSequentialGroup()
                .addComponent(jbnCerrarPedido2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpBarraSuperiorPedido3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBarraSuperiorPedido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbTituloPedido2)
                    .addComponent(jlbTituloPedido3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpDetallePedido.add(jpBarraSuperiorPedido3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jpPanelLogoDetallePedido.setBackground(new java.awt.Color(204, 204, 255));

        jlbIconoPedido1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pedido_128.png"))); // NOI18N

        jlbLogoPrograma3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/SplashLogo.png"))); // NOI18N

        javax.swing.GroupLayout jpPanelLogoDetallePedidoLayout = new javax.swing.GroupLayout(jpPanelLogoDetallePedido);
        jpPanelLogoDetallePedido.setLayout(jpPanelLogoDetallePedidoLayout);
        jpPanelLogoDetallePedidoLayout.setHorizontalGroup(
            jpPanelLogoDetallePedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPanelLogoDetallePedidoLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jlbIconoPedido1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPanelLogoDetallePedidoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlbLogoPrograma3))
        );
        jpPanelLogoDetallePedidoLayout.setVerticalGroup(
            jpPanelLogoDetallePedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPanelLogoDetallePedidoLayout.createSequentialGroup()
                .addComponent(jlbLogoPrograma3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(jlbIconoPedido1)
                .addContainerGap())
        );

        jpDetallePedido.add(jpPanelLogoDetallePedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 300, 550));

        jlbNombreProveedor1.setDisplayedMnemonic('b');
        jlbNombreProveedor1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbNombreProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNombreProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Contacts_25px.png"))); // NOI18N
        jlbNombreProveedor1.setText("Cliente");
        jlbNombreProveedor1.setToolTipText("");
        jlbNombreProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbNombreProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDetallePedido.add(jlbNombreProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 120, -1));

        jSeparator51.setForeground(new java.awt.Color(0, 0, 0));
        jpDetallePedido.add(jSeparator51, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 460, 10));

        jtxClientePedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxClientePedido.setForeground(new java.awt.Color(102, 102, 102));
        jtxClientePedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxClientePedido.setBorder(null);
        jpDetallePedido.add(jtxClientePedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 450, 30));

        jSeparator52.setForeground(new java.awt.Color(0, 0, 0));
        jpDetallePedido.add(jSeparator52, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 550, 10));

        jtxDomicilioPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxDomicilioPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxDomicilioPedido.setBorder(null);
        jpDetallePedido.add(jtxDomicilioPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 540, 30));

        jlbDomicilioProveedor1.setDisplayedMnemonic('d');
        jlbDomicilioProveedor1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbDomicilioProveedor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbDomicilioProveedor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Home_25px_3.png"))); // NOI18N
        jlbDomicilioProveedor1.setText("Domicilio:");
        jlbDomicilioProveedor1.setToolTipText("");
        jlbDomicilioProveedor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbDomicilioProveedor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDetallePedido.add(jlbDomicilioProveedor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 120, 20));

        jSeparator53.setForeground(new java.awt.Color(0, 0, 0));
        jpDetallePedido.add(jSeparator53, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, 260, 10));

        jtxTelefonoPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxTelefonoPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxTelefonoPedido.setBorder(null);
        jpDetallePedido.add(jtxTelefonoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 240, 30));

        jlbTelefonoPedido.setDisplayedMnemonic('b');
        jlbTelefonoPedido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbTelefonoPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTelefonoPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Phone_25px.png"))); // NOI18N
        jlbTelefonoPedido.setText("Telefono:");
        jlbTelefonoPedido.setToolTipText("");
        jlbTelefonoPedido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbTelefonoPedido.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDetallePedido.add(jlbTelefonoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 120, 30));

        jlbfolioPedido.setDisplayedMnemonic('b');
        jlbfolioPedido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jlbfolioPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbfolioPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Pin Code_25px_1.png"))); // NOI18N
        jlbfolioPedido.setText("Folio");
        jlbfolioPedido.setToolTipText("");
        jlbfolioPedido.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jlbfolioPedido.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jpDetallePedido.add(jlbfolioPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 140, -1));

        jSeparator60.setForeground(new java.awt.Color(0, 0, 0));
        jpDetallePedido.add(jSeparator60, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 120, 10));

        jtxfolioPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxfolioPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxfolioPedido.setBorder(null);
        jpDetallePedido.add(jtxfolioPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 120, 20));

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane12.setBorder(null);
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jtbPedido1.setAutoCreateRowSorter(true);
        jtbPedido1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jtbPedido1.setModel(modeloPedido);
        jtbPedido1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jtbPedido1.setGridColor(new java.awt.Color(255, 255, 255));
        jtbPedido1.setRowHeight(25);
        jtbPedido1.setRowMargin(8);
        jtbPedido1.setSelectionBackground(new java.awt.Color(22, 114, 185));
        jScrollPane12.setViewportView(jtbPedido1);

        jpDetallePedido.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 860, 220));

        jpPrincipal.add(jpDetallePedido, "card14");

        getContentPane().add(jpPrincipal, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarActionPerformed

    private void jbnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnProductoActionPerformed
        abrirOpcion(jpPrincipal, jpProducto);
        actualizarListaProductos();
    }//GEN-LAST:event_jbnProductoActionPerformed

    private void jbnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnPedidosActionPerformed
        abrirOpcion(jpPrincipal, jpPedido);
        LimpiarTabla(jtbPedido, modeloPedido);
        objPedido.consultaPedido(modeloPedido);
    }//GEN-LAST:event_jbnPedidosActionPerformed

    private void jbnDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnDescuentoActionPerformed
        abrirOpcion(jpPrincipal, jpOpcionesyAsistencia);
    }//GEN-LAST:event_jbnDescuentoActionPerformed

    private void jbnEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEmpleadoActionPerformed
        abrirOpcion(jpPrincipal, jpEmpleado);
        LimpiarTabla(jtbEmpleado, modeloEmpleado);
        objEmpleado.consultaEmpleado(modeloEmpleado);

    }//GEN-LAST:event_jbnEmpleadoActionPerformed

    private void jbnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnProveedorActionPerformed
        abrirOpcion(jpPrincipal, jpProveedor);
        LimpiarTabla(jtbProveedor, modeloProveedor);
        objProveedor.consultaProveedor(modeloProveedor);

    }//GEN-LAST:event_jbnProveedorActionPerformed

    private void jbnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnVentaActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        abrirOpcion(jpPrincipal, jpVenta);
        LimpiarTabla(jtbProductoVenta, modeloProductoVenta);
        objProductoVenta.consultaProducto(modeloProductoVenta);
        objCliente.ConsultarNombre(comboClienteVenta);
        objVenta.setIdVenta(objVenta.MaximoidOrden()+1);
        objVenta.setFechaVenta(java.sql.Date.valueOf(dateFormat.format(date)));
        objVenta.setSubtotal(0);
        objVenta.setTotal(0);
        objVenta.setTotalaDescontar(0);
        objVenta.setIva(0);
        objVenta.setIdDescuento(0);
        objVenta.altaVenta();
            
    }//GEN-LAST:event_jbnVentaActionPerformed

    private void jbnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnClientesActionPerformed
        abrirOpcion(jpPrincipal, jpCliente);
        LimpiarTabla(jtbCliente, modeloCliente);
        objCliente.consultaCliente(modeloCliente);
    }//GEN-LAST:event_jbnClientesActionPerformed

    private void jbnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCompraActionPerformed
        abrirOpcion(jpPrincipal, jpCompra);

    }//GEN-LAST:event_jbnCompraActionPerformed

    private void jbnRerportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRerportesActionPerformed
        abrirOpcion(jpPrincipal, jPedido1);
        LimpiarTabla(jtbPedido2, modeloPedido);
        objPedido.consultaPedido(modeloPedido);
    }//GEN-LAST:event_jbnRerportesActionPerformed

    private void jbnEmpresasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEmpresasActionPerformed
        abrirOpcion(jpPrincipal, jpEmpresa);
    }//GEN-LAST:event_jbnEmpresasActionPerformed

    private void jbnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrar1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrar1ActionPerformed

    private void jbnRegresarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarClienteActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarClienteActionPerformed

    private void jbnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnActualizarClienteActionPerformed

        objCliente.setEmail(jtxEmailCliente.getText());
        objCliente.setRfc(jtxRfcCliente.getText());
        objCliente.setNombre(jtxNombreCliente.getText());
        objCliente.setApellidos(jtxApellidosClientes.getText());
        objCliente.setTelefono(jtxTelefonoCliente.getText());
        objCliente.setEstado(jtxEstadoCliente.getText());
        objCliente.setMunicipio(jtxMunicipioCliente.getText());
        objCliente.setCalleNumero(jtxCalleyNumCliente.getText());
        objCliente.setColonia(jtxColoniaCliente.getText());
        objCliente.setCp(Integer.parseInt(jtxCpCliente.getText()));
        objCliente.setReferencias(jtxReferenciaCliente.getText());
        objCliente.setIdEmpresa((int) jcbxEmpresaCliente.getSelectedIndex());
        objCliente.setfNac(jtxFechaNacimiento.getText());
        objCliente.setContrasena(jtxContrasenaCliente.getText());
        objCliente.modificarPersona();
        objCliente.modificarCliente();


    }//GEN-LAST:event_jbnActualizarClienteActionPerformed

    private void jbnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarClienteActionPerformed

        objCliente.setEmail(jtxEmailCliente.getText());
        objCliente.setRfc(jtxRfcCliente.getText());
        objCliente.setNombre(jtxNombreCliente.getText());
        objCliente.setApellidos(jtxApellidosClientes.getText());
        objCliente.setTelefono(jtxTelefonoCliente.getText());
        objCliente.setEstado(jtxEstadoCliente.getText());
        objCliente.setMunicipio(jtxMunicipioCliente.getText());
        objCliente.setCalleNumero(jtxCalleyNumCliente.getText());
        objCliente.setColonia(jtxColoniaCliente.getText());
        objCliente.setCp(Integer.parseInt(jtxCpCliente.getText()));
        objCliente.setReferencias(jtxReferenciaCliente.getText());
        objCliente.setIdEmpresa((int) jcbxEmpresaCliente.getSelectedIndex());
        objCliente.setfNac(jtxFechaNacimiento.getText());
        objCliente.setContrasena(jtxContrasenaCliente.getText());
        objCliente.altaPersona();
        objCliente.altaCliente();
    }//GEN-LAST:event_jbnAgregarClienteActionPerformed

    private void jbnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarClienteActionPerformed
        if (Integer.parseInt(jtxIdCliente.getText()) != 0) {
            objCliente.bajaCliente();
        }
    }//GEN-LAST:event_jbnEliminarClienteActionPerformed

    private void jbnCerrarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarEmpleadoActionPerformed
        System.exit(0); //cierra el formulario
    }//GEN-LAST:event_jbnCerrarEmpleadoActionPerformed

    private void jbnRegresarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarEmpleadoActionPerformed
        regresarMenu();
        //Se inhabilitan los botones de actualizar de nueva cuenta
        jbnEliminarEmpleado.setEnabled(false);
        jbnActualizarEmpleado.setEnabled(false);
    }//GEN-LAST:event_jbnRegresarEmpleadoActionPerformed

    private void jbnActualizarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnActualizarEmpleadoActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        objEmpleado.setUsuario(jtxUsuarioEmpleado.getText());
        objEmpleado.setContrasena(jtxContrasenaEmpleado.getText());
        objEmpleado.setNss(Integer.parseInt(jtxNssEmpleado.getText()));
        objEmpleado.setEmail(jtxEmailEmpleado.getText());
        objEmpleado.setRfc(jtxRfcEmpleado.getText());
        objEmpleado.setNombre(jtxNombreEmpleado.getText());
        objEmpleado.setApellidos(jtxApellidosEmpleado.getText());
        objEmpleado.setfNac(dateFormat.format(date));
        objEmpleado.setTelefono(jtxTelefonoEmpleado.getText());
        objEmpleado.setEstado(jtxEstadoEmpleado.getText());
        objEmpleado.setMunicipio(jtxMunicipioEmpleado.getText());
        objEmpleado.setCalleNumero(jtxCalleyNumEmpleado.getText());
        objEmpleado.setColonia(jtxColoniaEmpleado.getText());
        objEmpleado.setCp(Integer.parseInt(jtxCpEmpleado.getText()));
        objEmpleado.setReferencias(jtxReferenciasEmpleado.getText());
        objEmpleado.modificarPersona();
        objEmpleado.modificarEmpleado();
    }//GEN-LAST:event_jbnActualizarEmpleadoActionPerformed

    private void jbnAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarEmpleadoActionPerformed
        objEmpleado.setUsuario(jtxUsuarioEmpleado.getText());
        objEmpleado.setContrasena(jtxContrasenaEmpleado.getText());
        objEmpleado.setNss(Integer.parseInt(jtxNssEmpleado.getText()));
        objEmpleado.setEmail(jtxEmailEmpleado.getText());
        objEmpleado.setRfc(jtxRfcEmpleado.getText());
        objEmpleado.setNombre(jtxNombreEmpleado.getText());
        objEmpleado.setApellidos(jtxApellidosEmpleado.getText());
        objEmpleado.setTelefono(jtxTelefonoEmpleado.getText());
        objEmpleado.setEstado(jtxEstadoEmpleado.getText());
        objEmpleado.setMunicipio(jtxMunicipioEmpleado.getText());
        objEmpleado.setCalleNumero(jtxCalleyNumEmpleado.getText());
        objEmpleado.setColonia(jtxColoniaEmpleado.getText());
        objEmpleado.setCp(Integer.parseInt(jtxCpEmpleado.getText()));
        objEmpleado.setReferencias(jtxReferenciasEmpleado.getText());
        objEmpleado.setIdEmpresa((int) jcbxEmpresaEmpleado.getSelectedIndex());
        objEmpleado.setfNac(jtxFechaNacimientoEmpl.getText());
        objEmpleado.altaPersona();
        objEmpleado.altaEmpleado();
    }//GEN-LAST:event_jbnAgregarEmpleadoActionPerformed

    private void jbnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarEmpleadoActionPerformed
        if (Integer.parseInt(jtxIdEmpleado.getText()) != 0) {
            objEmpleado.bajaEmpleado();

        }
    }//GEN-LAST:event_jbnEliminarEmpleadoActionPerformed

    private void jbnCerrarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarEmpresaActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarEmpresaActionPerformed

    private void jbnRegresarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarEmpresaActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarEmpresaActionPerformed

    private void jbnActualizarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnActualizarEmpresaActionPerformed
        objEmpresa.setNombreEmpresa(jtxNombreEmpresa.getText());
        objEmpresa.setRfcEmpresa(jtxRfcEmpresa.getText());
        objEmpresa.setDomicilio(jtxDomicilioEmpresa.getText());
        objEmpresa.setTelefono(jtxTelefonoEmpresa.getText());
        objEmpresa.setEmail(jtxEmailEmpresa.getText());
        objEmpresa.setLocalidad(jtxLocalidadEmpresa.getText());
        objEmpresa.setNoEmpleados(Integer.parseInt(jtxNumEmpleadosEmpresa.getText()));
        objEmpresa.modificarEmpresa();
    }//GEN-LAST:event_jbnActualizarEmpresaActionPerformed

    private void jbnAgregarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnAgregarEmpresaActionPerformed

    private void jbnEliminarCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarCliente2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnEliminarCliente2ActionPerformed

    private void jpOpcionRedesEmpresaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionRedesEmpresaMouseEntered
        setColorEmpresa(jpOpcionRedesEmpresa, jlbOpcionNombreRedes);
    }//GEN-LAST:event_jpOpcionRedesEmpresaMouseEntered

    private void jpOpcionRedesEmpresaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionRedesEmpresaMouseExited
        resetColorEmpresa(jpOpcionRedesEmpresa, jlbOpcionNombreRedes);
    }//GEN-LAST:event_jpOpcionRedesEmpresaMouseExited

    private void jpOpcionTritonEmpresaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionTritonEmpresaMouseEntered
        setColorEmpresa(jpOpcionTritonEmpresa, jlbOpcionNombreTriton);
    }//GEN-LAST:event_jpOpcionTritonEmpresaMouseEntered

    private void jpOpcionTritonEmpresaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionTritonEmpresaMouseExited
        resetColorEmpresa(jpOpcionTritonEmpresa, jlbOpcionNombreTriton);
    }//GEN-LAST:event_jpOpcionTritonEmpresaMouseExited

    private void jbnCerrarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarPedidoActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarPedidoActionPerformed

    private void jbnRegresarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarPedidoActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarPedidoActionPerformed

    private void jpOpcionRedesEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionRedesEmpresaMouseClicked

        jlbLogoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png")));
        jpBarraInferiorEmpresa.setBackground(new Color(22, 114, 185));
        jpBarraSuperiorEmpresa.setBackground(new Color(22, 114, 185));
        jtxBuscarEmpresa.setBackground(new Color(22, 114, 185));
        objEmpresa.consultaEmpresa(1);
        jtxNombreEmpresa.setText(objEmpresa.getNombreEmpresa());
        jtxEmailEmpresa.setText(objEmpresa.getEmail());
        jtxRfcEmpresa.setText(objEmpresa.getRfcEmpresa());
        jtxDomicilioEmpresa.setText(objEmpresa.getDomicilio());
        jtxTelefonoEmpresa.setText(objEmpresa.getTelefono());
        jtxLocalidadEmpresa.setText(objEmpresa.getLocalidad());
        jtxNumEmpleadosEmpresa.setText(String.valueOf(objEmpresa.getNoEmpleados()));


    }//GEN-LAST:event_jpOpcionRedesEmpresaMouseClicked

    private void jpOpcionTritonEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpOpcionTritonEmpresaMouseClicked
        //Se cambia el color y el icono de la aplicación al darle click al panel
        jlbLogoEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoTriton.png")));
        jpBarraInferiorEmpresa.setBackground(new Color(153, 0, 0));
        jpBarraSuperiorEmpresa.setBackground(new Color(153, 0, 0));
        jtxBuscarEmpresa.setBackground(new Color(153, 0, 0));
        //Se realiza consulta a la base de datos
        objEmpresa.consultaEmpresa(2);
        //Se llenan campos, con los datos resultantes de la consulta
        jtxNombreEmpresa.setText(objEmpresa.getNombreEmpresa());
        jtxEmailEmpresa.setText(objEmpresa.getEmail());
        jtxRfcEmpresa.setText(objEmpresa.getRfcEmpresa());
        jtxDomicilioEmpresa.setText(objEmpresa.getDomicilio());
        jtxTelefonoEmpresa.setText(objEmpresa.getTelefono());
        jtxLocalidadEmpresa.setText(objEmpresa.getLocalidad());
        jtxNumEmpleadosEmpresa.setText(String.valueOf(objEmpresa.getNoEmpleados()));

    }//GEN-LAST:event_jpOpcionTritonEmpresaMouseClicked

    private void jbnCerrarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarProveedorActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarProveedorActionPerformed

    private void jbnRegresarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarProveedorActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarProveedorActionPerformed

    private void jbnActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnActualizarProveedorActionPerformed
        objProveedor.setIdProveedor(Integer.parseInt(jtxIdProveedor.getText()));
        objProveedor.setNombre(jtxNombreProveedor.getText());
        objProveedor.setRfc(jtxRfcProveedor.getText());
        objProveedor.setEmail(jtxEmailProveedor.getText());
        objProveedor.setTelefono(jtxTelefonoProveedor.getText());
        objProveedor.setDomicilio(jtxDomicilioProveedor.getText());
        objProveedor.modificarProveedor();
    }//GEN-LAST:event_jbnActualizarProveedorActionPerformed

    private void jbnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarProveedorActionPerformed
        objProveedor.setIdProveedor(Integer.parseInt(jtxIdProveedor.getText()));
        objProveedor.setNombre(jtxNombreProveedor.getText());
        objProveedor.setRfc(jtxRfcProveedor.getText());
        objProveedor.setEmail(jtxEmailProveedor.getText());
        objProveedor.setTelefono(jtxTelefonoProveedor.getText());
        objProveedor.setDomicilio(jtxDomicilioProveedor.getText());
        objProveedor.altaProveedor();
    }//GEN-LAST:event_jbnAgregarProveedorActionPerformed

    private void jbnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarProveedorActionPerformed
        if (objProveedor.getIdProveedor() != 0) {
            objProveedor.bajaProveedor();
        }
    }//GEN-LAST:event_jbnEliminarProveedorActionPerformed

    private void jpInformacionAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpInformacionAsistenciaMouseClicked
        //Se manda llamar metedo para abrir panel
        abrirOpcion(jpOpcionesContenido, jpOpcionesPoliticas);

    }//GEN-LAST:event_jpInformacionAsistenciaMouseClicked

    private void jpInformacionAsistenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpInformacionAsistenciaMouseEntered
        setColorOpciones(jpInformacionAsistencia);
        resetColorOpciones(jpPoliticasAsistencia);
    }//GEN-LAST:event_jpInformacionAsistenciaMouseEntered

    private void jpInformacionAsistenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpInformacionAsistenciaMouseExited
        resetColorOpciones(jpInformacionAsistencia);

    }//GEN-LAST:event_jpInformacionAsistenciaMouseExited

    private void jpPoliticasAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPoliticasAsistenciaMouseClicked
        abrirOpcion(jpOpcionesContenido, jpOpcionDescuento);
    }//GEN-LAST:event_jpPoliticasAsistenciaMouseClicked

    private void jpPoliticasAsistenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPoliticasAsistenciaMouseEntered
        setColorOpciones(jpPoliticasAsistencia);
    }//GEN-LAST:event_jpPoliticasAsistenciaMouseEntered

    private void jpPoliticasAsistenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPoliticasAsistenciaMouseExited
        resetColorOpciones(jpPoliticasAsistencia);
    }//GEN-LAST:event_jpPoliticasAsistenciaMouseExited

    private void jbnCerrarOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarOpcionesActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarOpcionesActionPerformed

    private void jbnRegresarOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarOpcionesActionPerformed
        regresarMenu();
        abrirOpcion(jpOpcionesContenido, jpOpcion1);
    }//GEN-LAST:event_jbnRegresarOpcionesActionPerformed

    private void jbnCerrarOpciones2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarOpciones2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnCerrarOpciones2ActionPerformed

    private void jbnCerrarOpciones1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarOpciones1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnCerrarOpciones1ActionPerformed

    private void jpPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPrincipalMousePressed
        // setOpacity((float)0.9);
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jpPrincipalMousePressed

    private void jpPrincipalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPrincipalMouseReleased
        //  setOpacity((float)1.0);
    }//GEN-LAST:event_jpPrincipalMouseReleased

    private void jpPrincipalMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpPrincipalMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jpPrincipalMouseDragged

    private void jbnCerrarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarProductoActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarProductoActionPerformed

    private void jbnRegresarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarProductoActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarProductoActionPerformed

    private void jbnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnActualizarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnActualizarProductoActionPerformed

    private void jbnAgregarProveedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarProveedor1ActionPerformed
        pcontrol.agegarProducto();
    }//GEN-LAST:event_jbnAgregarProveedor1ActionPerformed

    private void jbnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarProductoActionPerformed
        // TODO add your handling code here:Sysr
    }//GEN-LAST:event_jbnEliminarProductoActionPerformed

    private void jtxBuscarEmpleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarEmpleadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            objEmpleado.buscarEmpleado(jtxBuscarEmpleado.getText());
            jtxUsuarioEmpleado.setText(objEmpleado.getUsuario());
            jtxContrasenaEmpleado.setText(objEmpleado.getContrasena());
            jtxNssEmpleado.setText(String.valueOf(objEmpleado.getNss()));
            jtxEmailEmpleado.setText(objEmpleado.getEmail());
            jtxRfcEmpleado.setText(objEmpleado.getRfc());
            jtxNombreEmpleado.setText(objEmpleado.getNombre());
            jtxApellidosEmpleado.setText(objEmpleado.getApellidos());
            jtxFechaNacimientoEmpl.setText(objEmpleado.getfNac());
            jtxTelefonoEmpleado.setText(objEmpleado.getTelefono());
            jtxEstadoEmpleado.setText(objEmpleado.getEstado());
            jtxMunicipioEmpleado.setText(objEmpleado.getMunicipio());
            jtxCalleyNumEmpleado.setText(objEmpleado.getCalleNumero());
            jtxColoniaEmpleado.setText(objEmpleado.getColonia());
            jtxCpEmpleado.setText(String.valueOf(objEmpleado.getCp()));
            jtxReferenciasEmpleado.setText(objEmpleado.getReferencias());
            jtxIdEmpleado.setText(String.valueOf(objEmpleado.getIdEmpleado()));
            jcbxEmpresaEmpleado.setSelectedIndex(objEmpleado.getIdEmpresa());
            //se habilitan los botones en caso de que la consulta sea valida

            if (objEmpleado.getIdEmpleado() >= 1) {
                jbnEliminarEmpleado.setEnabled(true);
                jbnActualizarEmpleado.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jtxBuscarEmpleadoKeyPressed

    private void jtxBuscarEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarEmpleadoKeyTyped
        jtxBuscarEmpleado.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                repaint();
                filtroEmpleado();
            }
        });
        trsfiltro = new TableRowSorter(modeloEmpleado);
        jtbEmpleado.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxBuscarEmpleadoKeyTyped

    private void jtxBuscarClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarClienteKeyTyped
        jtxBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                repaint();
                filtroCliente();
            }
        });
        trsfiltro = new TableRowSorter(modeloCliente);
        jtbCliente.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxBuscarClienteKeyTyped

    private void jtxBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            objCliente.buscarCliente(jtxBuscarCliente.getText());
            jtxIdCliente.setText(String.valueOf(objCliente.getIdCliente()));
            jtxEmailCliente.setText(objCliente.getEmail());
            jtxRfcCliente.setText(objCliente.getRfc());
            jtxNombreCliente.setText(objCliente.getNombre());
            jtxApellidosClientes.setText(objCliente.getApellidos());
            jtxFechaNacimiento.setText(objCliente.getfNac());
            jtxTelefonoCliente.setText(objCliente.getTelefono());
            jtxEstadoCliente.setText(objCliente.getEstado());
            jtxMunicipioCliente.setText(objCliente.getMunicipio());
            jtxCalleyNumCliente.setText(objCliente.getCalleNumero());
            jtxColoniaCliente.setText(objCliente.getColonia());
            jtxCpCliente.setText(String.valueOf(objCliente.getCp()));
            jtxReferenciaCliente.setText(objCliente.getReferencias());
            jtxIdCliente.setText(String.valueOf(objCliente.getIdCliente()));
            jcbxEmpresaCliente.setSelectedIndex(objCliente.getIdEmpresa());
            jtxNoComprasCliente.setText(String.valueOf(objCliente.getNoCompras()));
            jtxContrasenaCliente.setText(objCliente.getContrasena());
            //se habilitan los botones en caso de que la consulta sea valida

            if (objCliente.getIdCliente() >= 1) {
                jbnEliminarCliente.setEnabled(true);
                jbnActualizarCliente.setEnabled(true);
            }
        }
    }//GEN-LAST:event_jtxBuscarClienteKeyPressed

    private void jtxBuscarProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarProveedorKeyTyped
        jtxBuscarProveedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                repaint();
                filtroProveedor();
            }
        });
        trsfiltro = new TableRowSorter(modeloProveedor);
        jtbProveedor.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxBuscarProveedorKeyTyped

    private void jtxBuscarProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarProveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            objProveedor.buscarProveedor(jtxBuscarProveedor.getText());
            jtxIdProveedor.setText(String.valueOf(objProveedor.getIdProveedor()));
            jtxNombreProveedor.setText(objProveedor.getNombre());
            jtxDomicilioProveedor.setText(objProveedor.getDomicilio());
            jtxEmailProveedor.setText(objProveedor.getEmail());
            jtxTelefonoProveedor.setText(objProveedor.getTelefono());
            jtxRfcProveedor.setText(objProveedor.getRfc());
            //se habilitan los botones en caso de que la consulta sea valida

            if (objEmpleado.getIdEmpleado() >= 1) {
                jbnEliminarEmpleado.setEnabled(true);
                jbnActualizarEmpleado.setEnabled(true);
            }
        }


    }//GEN-LAST:event_jtxBuscarProveedorKeyPressed


    private void jbnCerrarPedido1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarPedido1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarPedido1ActionPerformed

    private void jbnRegresarPedido1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarPedido1ActionPerformed
        regresarMenu();
    }//GEN-LAST:event_jbnRegresarPedido1ActionPerformed

    private void jcbxEstatusPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxEstatusPedidoActionPerformed
        if ((jcbxEstatusPedido.getSelectedIndex()) == 0) {
            modeloPedido.setRowCount(0);
            objPedido.consultaPedido(modeloPedido);
        }
        if ((jcbxEstatusPedido.getSelectedIndex()) == 1) {
            modeloPedido.setRowCount(0);
            objPedido.setEstatus(((String) jcbxEstatusPedido.getSelectedItem()));
            objPedido.consultaEstatus(modeloPedido);
        }
        if ((jcbxEstatusPedido.getSelectedIndex()) == 2) {
            modeloPedido.setRowCount(0);
            objPedido.setEstatus(((String) jcbxEstatusPedido.getSelectedItem()));
            objPedido.consultaEstatus(modeloPedido);
        }
        if ((jcbxEstatusPedido.getSelectedIndex()) == 3) {
            modeloPedido.setRowCount(0);
            objPedido.setEstatus(((String) jcbxEstatusPedido.getSelectedItem()));
            objPedido.consultaEstatus(modeloPedido);
        }
        if ((jcbxEstatusPedido.getSelectedIndex()) == 4) {
            modeloPedido.setRowCount(0);
            objPedido.setEstatus(((String) jcbxEstatusPedido.getSelectedItem()));
            objPedido.consultaEstatus(modeloPedido);
        }
    }//GEN-LAST:event_jcbxEstatusPedidoActionPerformed

    private void jtxBuscarPedidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarPedidoKeyTyped
        jtxBuscarPedido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                repaint();
                filtroPedido();
            }
        });
        trsfiltro = new TableRowSorter(modeloPedido);
        jtbPedido.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxBuscarPedidoKeyTyped

    private void jtxBuscarPedidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarPedidoKeyPressed

    }//GEN-LAST:event_jtxBuscarPedidoKeyPressed

    private void jbnCerrarPedido2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarPedido2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnCerrarPedido2ActionPerformed

    private void jbnRegresarPedido2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarPedido2ActionPerformed
        abrirOpcion(jpPrincipal, jpPedido);
    }//GEN-LAST:event_jbnRegresarPedido2ActionPerformed

    private void jtxBuscarProductoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarProductoVentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxBuscarProductoVentaKeyPressed

    private void jtxBuscarProductoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxBuscarProductoVentaKeyTyped
        jtxBuscarProductoVenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                repaint();
                filtroPedido();
            }
        });
        trsfiltro = new TableRowSorter(modeloPedido);
        jtbPedido.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxBuscarProductoVentaKeyTyped

    private void jcbxClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxClienteVentaActionPerformed

    private void jtxPagoPor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxPagoPor1ActionPerformed
        // jbnFinalizarVenta.requestFocus();
    }//GEN-LAST:event_jtxPagoPor1ActionPerformed

    private void jtxPagoPor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxPagoPor1KeyPressed
//        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//            pago=Double.parseDouble(jtxPagoPor1.getText());
//            cambio=Double.parseDouble(jtxTotalVenta.getText());
//            cambio=pago-cambio;
//            jtxCambio.setText(String.valueOf(cambio));
//        }
    }//GEN-LAST:event_jtxPagoPor1KeyPressed

    private void jtxPagoPor1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxPagoPor1KeyTyped
        //CONVERTIR LA TECLA PRESIONADA EN TIPO CHAR
        char letra = evt.getKeyChar();

        //VALIDAR SI ES NUMERO
        if (!Character.isDigit(letra)) {
            getToolkit().beep();//Emite un sonido de alerta
            evt.consume(); // evita que el caracter aparezca en la caja  de texto
        }
    }//GEN-LAST:event_jtxPagoPor1KeyTyped

    private void jbnCerrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarVentaActionPerformed
        objVenta.setIdVenta(objVenta.MaximoidOrden());
        if(banderitaDetalleVentecita!=0){
        objDetalleVenta.bajaDetalleVenta(objVenta.getIdVenta());
        }
        objVenta.bajaVenta();
        System.exit(0);
    }//GEN-LAST:event_jbnCerrarVentaActionPerformed

    private void jbnAgregarProveedor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAgregarProveedor2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnAgregarProveedor2ActionPerformed

    private void jbnCerrarSesionVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCerrarSesionVentaActionPerformed
        
    }//GEN-LAST:event_jbnCerrarSesionVentaActionPerformed

    private void jbnEliminarProducto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEliminarProducto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnEliminarProducto2ActionPerformed

    private void jbnCancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnCancelarVentaActionPerformed
        regresarMenu();
        objVenta.setIdVenta(objVenta.MaximoidOrden());
        if(banderitaDetalleVentecita!=0){
        objDetalleVenta.bajaDetalleVenta(objVenta.getIdVenta());
        }
        objVenta.bajaVenta();
        
     JOptionPane.showMessageDialog(null,"Venta cancelada");
    }//GEN-LAST:event_jbnCancelarVentaActionPerformed

    private void jbnRegresarEmpleado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarEmpleado1ActionPerformed
     regresarMenu();
        objVenta.setIdVenta(objVenta.MaximoidOrden());
        if(banderitaDetalleVentecita!=0){
        objDetalleVenta.bajaDetalleVenta(objVenta.getIdVenta());
        }
        objVenta.bajaVenta();
        
    }//GEN-LAST:event_jbnRegresarEmpleado1ActionPerformed

    //Metodos para cambiar color en el panel empresa
    void setColorEmpresa(JPanel panel, JLabel label) {
        panel.setBackground(new Color(22, 114, 185));
        label.setForeground(Color.white);
    }

    void resetColorEmpresa(JPanel panel, JLabel label) {
        panel.setBackground(new Color(225, 225, 225));
        label.setForeground(Color.black);
    }

    //metodos para cambiar color en la opciones de panel empresa 
    void setColorOpciones(JPanel panel) {
        panel.setBackground(new Color(0, 51, 255));

    }

    void resetColorOpciones(JPanel panel) {
        panel.setBackground(new Color(51, 153, 255));

    }

    //Metodo para regrsar al menu desde cualquier ventana
    void regresarMenu() {
        jpPrincipal.removeAll();
        jpPrincipal.add(jpMenu);
        jpMenu.repaint();
        jpMenu.revalidate();
    }
    //Metodo para abrir los panels en cart layout

    void abrirOpcion(JPanel panel, JPanel panelito) {
        panel.removeAll();
        panel.add(panelito);
        panelito.repaint();
        panelito.revalidate();
    }
    
    void actualizarListaProductos(){
                try {
            pcontrol.initUI();
        } catch (IOException ex) {
            Logger.getLogger(frmMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodos para limpiar campos y tablas cada vez que entras a una opción
    public void LimpiarTabla(JTable tablita, DefaultTableModel modelito) {
        for (int i = 0; i < tablita.getRowCount(); i++) {
            modelito.removeRow(i);
            i -= 1;
        }

        tablita.removeAll();
        modelito.getColumnCount();
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new frmMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Prueba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPMenuEmpresa;
    private javax.swing.JPanel jPMenuPedido;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPedido1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JSeparator jSeparator39;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator40;
    private javax.swing.JSeparator jSeparator41;
    private javax.swing.JSeparator jSeparator42;
    private javax.swing.JSeparator jSeparator43;
    private javax.swing.JSeparator jSeparator44;
    private javax.swing.JSeparator jSeparator45;
    private javax.swing.JSeparator jSeparator46;
    private javax.swing.JSeparator jSeparator47;
    private javax.swing.JSeparator jSeparator48;
    private javax.swing.JSeparator jSeparator49;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator50;
    private javax.swing.JSeparator jSeparator51;
    private javax.swing.JSeparator jSeparator52;
    private javax.swing.JSeparator jSeparator53;
    private javax.swing.JSeparator jSeparator54;
    private javax.swing.JSeparator jSeparator55;
    private javax.swing.JSeparator jSeparator56;
    private javax.swing.JSeparator jSeparator57;
    private javax.swing.JSeparator jSeparator58;
    private javax.swing.JSeparator jSeparator59;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator60;
    private javax.swing.JSeparator jSeparator61;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbnActualizarCliente;
    private javax.swing.JButton jbnActualizarEmpleado;
    private javax.swing.JButton jbnActualizarEmpresa;
    private javax.swing.JButton jbnActualizarProducto;
    private javax.swing.JButton jbnActualizarProveedor;
    private javax.swing.JButton jbnAgregarCliente;
    private javax.swing.JButton jbnAgregarEmpleado;
    private javax.swing.JButton jbnAgregarEmpresa;
    private javax.swing.JButton jbnAgregarProveedor;
    private javax.swing.JButton jbnAgregarProveedor1;
    private javax.swing.JButton jbnAgregarProveedor2;
    private javax.swing.JButton jbnCancelarVenta;
    private javax.swing.JButton jbnCerrar;
    private javax.swing.JButton jbnCerrar1;
    private javax.swing.JButton jbnCerrarEmpleado;
    private javax.swing.JButton jbnCerrarEmpresa;
    private javax.swing.JButton jbnCerrarOpciones;
    private javax.swing.JButton jbnCerrarOpciones1;
    private javax.swing.JButton jbnCerrarOpciones2;
    private javax.swing.JButton jbnCerrarPedido;
    private javax.swing.JButton jbnCerrarPedido1;
    private javax.swing.JButton jbnCerrarPedido2;
    private javax.swing.JButton jbnCerrarProducto;
    private javax.swing.JButton jbnCerrarProveedor;
    private javax.swing.JButton jbnCerrarSesionVenta;
    private javax.swing.JButton jbnCerrarVenta;
    private javax.swing.JButton jbnClientes;
    private javax.swing.JButton jbnCompra;
    private javax.swing.JButton jbnDescuento;
    private javax.swing.JButton jbnEliminarCliente;
    private javax.swing.JButton jbnEliminarCliente2;
    private javax.swing.JButton jbnEliminarEmpleado;
    private javax.swing.JButton jbnEliminarProducto;
    private javax.swing.JButton jbnEliminarProducto2;
    private javax.swing.JButton jbnEliminarProveedor;
    private javax.swing.JButton jbnEmpleado;
    private javax.swing.JButton jbnEmpresas;
    private javax.swing.JButton jbnPedidos;
    private javax.swing.JButton jbnProducto;
    private javax.swing.JButton jbnProveedor;
    private javax.swing.JButton jbnRegresarCliente;
    private javax.swing.JButton jbnRegresarEmpleado;
    private javax.swing.JButton jbnRegresarEmpleado1;
    private javax.swing.JButton jbnRegresarEmpresa;
    private javax.swing.JButton jbnRegresarOpciones;
    private javax.swing.JButton jbnRegresarPedido;
    private javax.swing.JButton jbnRegresarPedido1;
    private javax.swing.JButton jbnRegresarPedido2;
    private javax.swing.JButton jbnRegresarProducto;
    private javax.swing.JButton jbnRegresarProveedor;
    private javax.swing.JButton jbnRerportes;
    private javax.swing.JButton jbnVenta;
    private javax.swing.JComboBox<String> jcbxClienteVenta;
    private javax.swing.JComboBox<String> jcbxEmpresaCliente;
    private javax.swing.JComboBox<String> jcbxEmpresaEmpleado;
    private javax.swing.JComboBox<String> jcbxEstatusPedido;
    private javax.swing.JLabel jlbApellidosCliente;
    private javax.swing.JLabel jlbApellidosEmpleado;
    private javax.swing.JLabel jlbBuscarCliente2;
    private javax.swing.JLabel jlbBuscarEmpleado;
    private javax.swing.JLabel jlbBuscarEmpresa;
    private javax.swing.JLabel jlbBuscarPedido;
    private javax.swing.JLabel jlbBuscarPedido1;
    private javax.swing.JLabel jlbBuscarPedido2;
    private javax.swing.JLabel jlbBuscarPedido3;
    private javax.swing.JLabel jlbBuscarPedido4;
    private javax.swing.JLabel jlbBuscarProducto;
    private javax.swing.JLabel jlbBuscarProveedor;
    private javax.swing.JLabel jlbCalleyNumCliente;
    private javax.swing.JLabel jlbCalleyNumEmpleado;
    private javax.swing.JLabel jlbCambio;
    private javax.swing.JLabel jlbClienteVenta;
    private javax.swing.JLabel jlbColoniaCliente;
    private javax.swing.JLabel jlbColoniaEmpleado;
    private javax.swing.JLabel jlbContrasenaCliente;
    private javax.swing.JLabel jlbContrasenaEmpleado;
    private javax.swing.JLabel jlbCpCliente;
    private javax.swing.JLabel jlbCpEmpleado;
    private javax.swing.JLabel jlbDomicilioEmpresa;
    private javax.swing.JLabel jlbDomicilioProveedor;
    private javax.swing.JLabel jlbDomicilioProveedor1;
    private javax.swing.JLabel jlbEmailCliente;
    private javax.swing.JLabel jlbEmailEmpleado;
    private javax.swing.JLabel jlbEmailEmpresa;
    private javax.swing.JLabel jlbEmailProveedor;
    private javax.swing.JLabel jlbEstadoCliente;
    private javax.swing.JLabel jlbEstadoEmpleado;
    private javax.swing.JLabel jlbEstatusPedido;
    private javax.swing.JLabel jlbFechaNacCliente;
    private javax.swing.JLabel jlbFechaNacEmpleado;
    private javax.swing.JLabel jlbIconoEmpleado;
    private javax.swing.JLabel jlbIconoEmresa;
    private javax.swing.JLabel jlbIconoOpciones;
    private javax.swing.JLabel jlbIconoPedido;
    private javax.swing.JLabel jlbIconoPedido1;
    private javax.swing.JLabel jlbIconoProducto;
    private javax.swing.JLabel jlbIconoProveedor;
    private javax.swing.JLabel jlbIdCliente;
    private javax.swing.JLabel jlbIdEmpleado;
    private javax.swing.JLabel jlbIdProveedor;
    private javax.swing.JLabel jlbIva;
    private javax.swing.JLabel jlbLocalidadEmpresa;
    private javax.swing.JLabel jlbLogo;
    private javax.swing.JLabel jlbLogoEmpresa;
    private javax.swing.JLabel jlbLogoEmpresaProveedor;
    private javax.swing.JLabel jlbLogoEmpresaProveedor1;
    private javax.swing.JLabel jlbLogoPrograma1;
    private javax.swing.JLabel jlbLogoPrograma2;
    private javax.swing.JLabel jlbLogoPrograma3;
    private javax.swing.JLabel jlbLogoProgramaProducto;
    private javax.swing.JLabel jlbLogoProgramaProveedor;
    private javax.swing.JLabel jlbLogoProgramaProveedor1;
    private javax.swing.JLabel jlbMunicipioCliente;
    private javax.swing.JLabel jlbMunicipioEmpleado;
    private javax.swing.JLabel jlbNoComprasCliente;
    private javax.swing.JLabel jlbNombreCliente;
    private javax.swing.JLabel jlbNombreEmpleado;
    private javax.swing.JLabel jlbNombreEmpleado1;
    private javax.swing.JLabel jlbNombreEmpleado2;
    private javax.swing.JLabel jlbNombreEmpresa;
    private javax.swing.JLabel jlbNombreProveedor;
    private javax.swing.JLabel jlbNombreProveedor1;
    private javax.swing.JLabel jlbNssEmpleado;
    private javax.swing.JLabel jlbNumEmpleadosEmpresa;
    private javax.swing.JLabel jlbOpcionNombreRedes;
    private javax.swing.JLabel jlbOpcionNombreRedes1;
    private javax.swing.JLabel jlbOpcionNombreRedes2;
    private javax.swing.JLabel jlbOpcionNombreTriton;
    private javax.swing.JLabel jlbPago;
    private javax.swing.JLabel jlbReferencialiente;
    private javax.swing.JLabel jlbReferenciasEmpleado;
    private javax.swing.JLabel jlbRfcCliente;
    private javax.swing.JLabel jlbRfcEmpleado;
    private javax.swing.JLabel jlbRfcEmpresa;
    private javax.swing.JLabel jlbRfcProveedor;
    private javax.swing.JLabel jlbSignoTotal;
    private javax.swing.JLabel jlbTelefonoCliente;
    private javax.swing.JLabel jlbTelefonoEmpleado;
    private javax.swing.JLabel jlbTelefonoEmpresa;
    private javax.swing.JLabel jlbTelefonoPedido;
    private javax.swing.JLabel jlbTelefonoProveedor;
    private javax.swing.JLabel jlbTituloCliente;
    private javax.swing.JLabel jlbTituloEmpleado;
    private javax.swing.JLabel jlbTituloEmpresa;
    private javax.swing.JLabel jlbTituloPedido;
    private javax.swing.JLabel jlbTituloPedido1;
    private javax.swing.JLabel jlbTituloPedido2;
    private javax.swing.JLabel jlbTituloPedido3;
    private javax.swing.JLabel jlbTituloProducto;
    private javax.swing.JLabel jlbTituloProveedor;
    private javax.swing.JLabel jlbTotalVenta1;
    private javax.swing.JLabel jlbTotalVenta3;
    private javax.swing.JLabel jlbUsuarioEmpleado;
    private javax.swing.JLabel jlbfolioPedido;
    private javax.swing.JPanel jpBarraInferiorCliente;
    private javax.swing.JPanel jpBarraInferiorCliente1;
    private javax.swing.JPanel jpBarraInferiorEmpresa;
    private javax.swing.JPanel jpBarraInferiorPedido;
    private javax.swing.JPanel jpBarraInferiorPedido1;
    private javax.swing.JPanel jpBarraInferiorPedido2;
    private javax.swing.JPanel jpBarraInferiorProducto;
    private javax.swing.JPanel jpBarraSuperiorCliente;
    private javax.swing.JPanel jpBarraSuperiorEmpleado;
    private javax.swing.JPanel jpBarraSuperiorEmpresa;
    private javax.swing.JPanel jpBarraSuperiorPedido;
    private javax.swing.JPanel jpBarraSuperiorPedido1;
    private javax.swing.JPanel jpBarraSuperiorPedido2;
    private javax.swing.JPanel jpBarraSuperiorPedido3;
    private javax.swing.JPanel jpBarraSuperiorProducto;
    private javax.swing.JPanel jpCliente;
    private javax.swing.JPanel jpCompra;
    public javax.swing.JPanel jpContenidoLogoProducto;
    private javax.swing.JPanel jpDescripcionPedido;
    private javax.swing.JPanel jpDetallePedido;
    private javax.swing.JPanel jpEmpleado;
    private javax.swing.JPanel jpEmpresa;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JPanel jpInferior;
    private javax.swing.JPanel jpInformacionAsistencia;
    public javax.swing.JPanel jpListaProd;
    private javax.swing.JPanel jpMenu;
    private javax.swing.JPanel jpOpcion1;
    private javax.swing.JPanel jpOpcionDescuento;
    private javax.swing.JPanel jpOpcionRedesEmpresa;
    private javax.swing.JPanel jpOpcionTritonEmpresa;
    private javax.swing.JPanel jpOpcionesContenido;
    private javax.swing.JPanel jpOpcionesMenu;
    private javax.swing.JPanel jpOpcionesPoliticas;
    private javax.swing.JPanel jpOpcionesyAsistencia;
    private javax.swing.JPanel jpPanelContenedor;
    private javax.swing.JPanel jpPanelInferiorVenta;
    javax.swing.JPanel jpPanelInformacion;
    private javax.swing.JPanel jpPanelLogoDetallePedido;
    private javax.swing.JPanel jpPanelSuperiorVenta;
    private javax.swing.JPanel jpPedido;
    private javax.swing.JPanel jpPestañaCompletado;
    private javax.swing.JPanel jpPestañaEnviado;
    private javax.swing.JPanel jpPestañaRecibido;
    private javax.swing.JPanel jpPestañaTransferido;
    private javax.swing.JPanel jpPoliticasAsistencia;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JPanel jpProducto;
    private javax.swing.JPanel jpProveedor;
    private javax.swing.JPanel jpReportes;
    private javax.swing.JPanel jpVenta;
    private javax.swing.JTable jtbCliente;
    public javax.swing.JTable jtbDetalleVenta;
    private javax.swing.JTable jtbEmpleado;
    private javax.swing.JTable jtbPedido;
    private javax.swing.JTable jtbPedido1;
    private javax.swing.JTable jtbPedido2;
    private javax.swing.JTable jtbPedidoRecibido;
    private javax.swing.JTable jtbPedidogenral;
    private javax.swing.JTable jtbProductoVenta;
    private javax.swing.JTable jtbProveedor;
    private javax.swing.JTable jtbTransferido;
    private javax.swing.JTextField jtxApellidosClientes;
    private javax.swing.JTextField jtxApellidosEmpleado;
    private javax.swing.JTextField jtxBuscarCliente;
    private javax.swing.JTextField jtxBuscarEmpleado;
    private javax.swing.JTextField jtxBuscarEmpresa;
    private javax.swing.JTextField jtxBuscarPedido;
    private javax.swing.JTextField jtxBuscarPedido1;
    private javax.swing.JTextField jtxBuscarProducto;
    private javax.swing.JTextField jtxBuscarProductoVenta;
    private javax.swing.JTextField jtxBuscarProveedor;
    private javax.swing.JTextField jtxCalleyNumCliente;
    private javax.swing.JTextField jtxCalleyNumEmpleado;
    private javax.swing.JTextField jtxCambio;
    private javax.swing.JTextField jtxClientePedido;
    private javax.swing.JTextField jtxColoniaCliente;
    private javax.swing.JTextField jtxColoniaEmpleado;
    private javax.swing.JTextField jtxContrasenaCliente;
    private javax.swing.JTextField jtxContrasenaEmpleado;
    private javax.swing.JTextField jtxCpCliente;
    private javax.swing.JTextField jtxCpEmpleado;
    private javax.swing.JTextField jtxDomicilioEmpresa;
    private javax.swing.JTextField jtxDomicilioPedido;
    private javax.swing.JTextField jtxDomicilioProveedor;
    private javax.swing.JTextField jtxEmailCliente;
    private javax.swing.JTextField jtxEmailEmpleado;
    private javax.swing.JTextField jtxEmailEmpresa;
    private javax.swing.JTextField jtxEmailProveedor;
    private javax.swing.JTextField jtxEstadoCliente;
    private javax.swing.JTextField jtxEstadoEmpleado;
    private javax.swing.JTextField jtxFechaNacimiento;
    private javax.swing.JTextField jtxFechaNacimientoEmpl;
    private javax.swing.JTextField jtxIdCliente;
    private javax.swing.JTextField jtxIdEmpleado;
    private javax.swing.JTextField jtxIdProveedor;
    public javax.swing.JTextField jtxIva;
    private javax.swing.JTextField jtxLocalidadEmpresa;
    private javax.swing.JTextField jtxMunicipioCliente;
    private javax.swing.JTextField jtxMunicipioEmpleado;
    private javax.swing.JTextField jtxNoComprasCliente;
    private javax.swing.JTextField jtxNombreCliente;
    private javax.swing.JTextField jtxNombreEmpleado;
    private javax.swing.JTextField jtxNombreEmpresa;
    private javax.swing.JTextField jtxNombreProveedor;
    private javax.swing.JTextField jtxNssEmpleado;
    private javax.swing.JTextField jtxNumEmpleadosEmpresa;
    private javax.swing.JTextField jtxPagoPor1;
    private javax.swing.JTextField jtxReferenciaCliente;
    private javax.swing.JTextField jtxReferenciasEmpleado;
    private javax.swing.JTextField jtxRfcCliente;
    private javax.swing.JTextField jtxRfcCliente6;
    private javax.swing.JTextField jtxRfcEmpleado;
    private javax.swing.JTextField jtxRfcEmpresa;
    private javax.swing.JTextField jtxRfcProveedor;
    public javax.swing.JTextField jtxSubtotalVenta;
    private javax.swing.JTextField jtxTelefonoCliente;
    private javax.swing.JTextField jtxTelefonoEmpleado;
    private javax.swing.JTextField jtxTelefonoEmpresa;
    private javax.swing.JTextField jtxTelefonoPedido;
    private javax.swing.JTextField jtxTelefonoProveedor;
    public javax.swing.JTextField jtxTotalVenta;
    private javax.swing.JTextField jtxUsuarioEmpleado;
    private javax.swing.JTextField jtxfolioPedido;
    // End of variables declaration//GEN-END:variables
}
