package com.gdw.util

import com.gdw.common.SparkEnv._
import org.apache.hadoop.fs._
import org.apache.hadoop.fs.permission.FsPermission


/**
  * Created by bselvaraj on 11/5/17.
  */

object Hdfs {

  val fs: FileSystem = hadoopFileSystem
  val fc: FileContext = hadoopFileContext

  def list(path: String, recursive: Boolean): Iterator[LocatedFileStatus] =
    HdfsFileIterator(fs.listFiles(new Path(path), recursive))

  def regxList(path: String): Array[FileStatus] =
    fs.globStatus(new Path(path))

  def exists(path: String): Boolean = fs.exists(new Path(path))

  def directorySize(path: String): Long = {
    val fsPath = new Path(path)
    if (fs.isDirectory(fsPath))
      fs.getContentSummary(fsPath).getLength
    else throw new IllegalArgumentException(s"'$path' is not a directory")
  }

  def mkdir(paths: Traversable[Path], createParent: Boolean): Unit =
    paths.foreach(path => fc.mkdir(path, FsPermission.getDefault, createParent))

  def mkdir(path: Path, createParent: Boolean): Unit =
    fc.mkdir(path, FsPermission.getDefault, createParent)

  def mkdir(path: String, createParent: Boolean): Unit =
    fc.mkdir(new Path(path), FsPermission.getDefault, createParent)

  def move(sourcePath: Path, targetPath: Path, overwrite: Boolean): Unit = {
    val renameOption = if (overwrite) Options.Rename.OVERWRITE else Options.Rename.NONE
    fc.rename(sourcePath, targetPath, renameOption)
  }

  case class HdfsFileIterator(remoteIterator: RemoteIterator[LocatedFileStatus]) extends Iterator[LocatedFileStatus] {
    override def hasNext: Boolean = remoteIterator.hasNext

    override def next(): LocatedFileStatus = remoteIterator.next()
  }

}

